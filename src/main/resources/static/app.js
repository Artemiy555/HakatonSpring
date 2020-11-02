let stompClient = null;
const uid = sessionStorage.getItem("token");
let gid = null;
let gamestate = null;
let player = "X";

function connect() {
	let socket = new SockJS('/ttt-websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/ttt/gamestate/' + gid, function (data) {
			updateGamestate(JSON.parse(data.body));
		});
		stompClient.send("/ttt/join/" + gid, {}, JSON.stringify({'player': uid}));
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	console.log("Disconnected");
	
	$('#disconnectModal').modal('hide');
	
	$("#menu").show();
	$("#tictactoe").hide();
	
	cleanGamestate();
	
	if (gid != null) {
		$.ajax({
			url: "/ttt/game",
			type: "patch",
			data: {
				id: gid,
				player: uid,
				disconnect: true
			}
		}).done(refresh);
	}
}

function rematch() {
	if (gamestate.winner || gamestate.draw) {
		$.ajax({
			url: "/ttt/game",
			type: "patch",
			data: {
				id: gid,
				player: uid,
				rematch: true
			}
		});
	}
}

function sendMove(x, y) {
	stompClient.send("/ttt/move/" + gid, {}, JSON.stringify({'player': uid, 'x': x, 'y': y}));
}

function refresh() {
	$.getJSON("/ttt/games/", function(data){
		$("#games").empty();
				
		if (data.length > 0) {
			for (let game in data) {
				game = data[game];
				$("#games").append("<tr><td><button id=\"" + game.id + "\" class=\"btn btn-success btn-sm\">Join</button>&nbsp;" + game.name +"  "+game.bet+"$" + "</td></tr>");
			}
		}
		else {
			$("#games").append("<tr><td>There are no games currently available. Try creating your own!</td></tr>");
		}
	});
}

function create() {
	let name = $("#gamename").val() || undefined;
	let bet = $("#bet").val() || undefined;


	$('#createGameModal').modal('hide');
	
	$.post({
		url: "/ttt/game",
		data: { 
			player: uid,
			name: name,
			bet: bet
		}
	}).done(function(data) {
		console.log("Created Game");
		
		player = "X";
		
		$("#menu").hide();
		$("#tictactoe").show();
		
		gid = data.id;
		connect();
		
		updateGamestate(data);
	});
}

function join(id) {
	$.ajax({
		url: "/ttt/game",
		type: "patch",
		data: {
			player: uid,
			id: id
		}
	}).done(function(data) {
		if (!data) {
			alert("Game is no longer available.", refresh);
			refresh();
			return;
		}
		
		console.log("Joined Game");
		
		player = "O";
		
		$("#menu").hide();
		$("#tictactoe").show();
		
		gid = data.id;
		connect();
		
		updateGamestate(data);
	});
}

function updateGamestate(data) {
	gamestate = data;
	
	$("#rematch").prop("disabled", !canRematch());
	
	gameStatus();
	drawBoard();
}

function canRematch() {
	return !gamestate.disconnect && (gamestate.winner || gamestate.draw)
}

function cleanGamestate() {
	gamestate = null;
		
	for (let x = 0; x < 3; x++) {
		for (let y = 0; y < 3; y++) {
			$("#".concat(x).concat(y)).text("");
		}
	}
}

function drawBoard() {
	for (let x = 0; x < 3; x++) {
		for (let y = 0; y < 3; y++) {
			$("#".concat(x).concat(y)).text(gamestate.board[x][y]);
		}
	}
}

function gameStatus() {
	let status = "";
	let status2 = "";
	
	// status
	if (!gamestate.started) {
		status = "Waiting for second player...";
	}
	else if (gamestate.disconnect) {
		status = "Other player has disconnected!"
	}
	else {
		status = "Both players are here! You are '" + player + "'."
	}
	
	// status2
	
	if (gamestate.started && !gamestate.winner && !gamestate.draw) {
		status2 = "'" + gamestate.startingPlayer + "' goes first.";
	}
	else if (gamestate.winner) {
		if (gamestate.winner == uid) {
			status2 = "You win!";
		}
		else {
			status2 = "You lost!";
		}
	}
	else if (gamestate.draw) {
		status2 = "It's a draw!"
	}
	else {
		status2 = "";
	}
	
	$("#status").text(status);
	$("#status2").text(status2);
}

function randString(length) {
	let text = "";
	let alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	for (let i = 0; i < length; i++) {
		text += alphanum.charAt(Math.floor(Math.random() * alphanum.length));
	}

	return text;
}

let unloadCalled = false;
function doUnload() {
	if (!unloadCalled) {
		unloadCalled = true;
		disconnect();
	}
};

$(function () {
	$("form").on('submit', function (e) {
		e.preventDefault();
	});
	
	$("#tictactoe").hide();
	
	$("#refresh").click(function() { refresh(); });
	$("#create").click(function() { create(); });
	$("#disconnect").click(function() { disconnect(); });
	$("#rematch").click(function() { rematch(); });
	
	$("#games").on( "click", "button", function() {
		join( $(this).attr('id') );
	});
	
	$("#board").on( "click", "td", function() {
		sendMove($(this).attr('x'), $(this).attr('y'));
	});
	
	refresh();
});
