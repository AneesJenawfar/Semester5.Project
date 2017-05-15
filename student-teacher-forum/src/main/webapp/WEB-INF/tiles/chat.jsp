<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:url var="saveChat" value="/saveChat" />
<c:url var="getChats" value="/get-chats" />
<c:url var="newChats" value="/new-chats" />

<div class="col-md-8 col-md-offset-2">
	<div class="panel panel-primary">
		<div class="panel-footer">
			<div class="input-group">
				<input id="text-chat" type="text" class="form-control input-sm"
					placeholder="Type your message here..." /> <span
					class="input-group-btn">
					<button class="btn btn-warning btn-sm" id="btn-chat">Send</button>
				</span>
			</div>
		</div>
	</div>
</div>

<div class="col-md-10" >

	<div class="container clearfix">

		<div class="chat" style=" margin-right: 150px;">
			<div class="chat-history">
				<ul class="chat-ul">
					
				</ul>
			</div>
		</div>

	</div>
</div>

<script>
	function saveChat(text, actionUrl, userId, place) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
			jqXHR.setRequestHeader(header, token);
		});

		$.ajax({
			'url' : actionUrl,
			data : {
				'id' : userId,
				'text' : text,
				'place' : place
			},
			type : 'POST',
			success : function() {
				//alert("ok");
			},
			error : function() {
				//alert("error");
			}
		});
	}

	function addChat() {
		
		var text = $("#text-chat").val();
		if (text != "") {
			saveChat(text, "${saveChat}", "${id}", "${place}");

			//var d = new Date(item.time);
			//var myDate = dateFormat(d);
			/* var element = $('<li class="clearfix"><div class="message-data align-right"><span class="message-data-name">You</span> <i class="fa fa-circle me"></i></div><div class="message me-message float-right">'
					+ text + '</div></li>');
			$(".chat-ul").prepend(element); */
			//$(".chat-ul").append(element);
			$("#text-chat").val('');
		} else {
			alert("Please enter a valid comment");
		}
	}

	function getChats(actionurl, id) {
		$.ajax({
			type : "GET",
			url : actionurl,
			data : {
				'id' : id
			},
			success : function(data) {
				result = data;
				$(".chat-ul").empty();
				printChats(result);
			}
		});
	}

	function dateFormat(d) {
		var myDate = ("00" + (d.getMonth() + 1)).slice(-2) + "/"
				+ ("00" + d.getDate()).slice(-2) + "/" + d.getFullYear() + " "
				+ ("00" + d.getHours()).slice(-2) + ":"
				+ ("00" + d.getMinutes()).slice(-2) + ":"
				+ ("00" + d.getSeconds()).slice(-2);
		return myDate
	}

	function printChats(data) {

		data
				.forEach(function(item) {
					
					if (("${place}" == "first" && item.owner == true)
							|| ("${place}" == "second" && item.owner == false)) {

						//var d = new Date(item.time);
						//var myDate = dateFormat(d);
						var element = $('<li class="clearfix"><div class="message-data align-right"><span class="message-data-name">You</span> <i class="fa fa-circle me"></i></div><div class="message me-message float-right">'
								+ item.text + '</div></li>');
						$(".chat-ul").prepend(element);

					} else if ("${place}" == "first" && item.owner == false) {

						//var d = new Date(item.time);
						//var myDate = dateFormat(d);
						var element = $('<li><div class="message-data"><span class="message-data-name"><i class="fa fa-circle you"></i>'
								+ item.user2.firstname
								+ '&nbsp;'
								+ item.user2.surname
								+ '</span></div><div class="message you-message">'
								+ item.text + '</div></li>');
						$(".chat-ul").prepend(element);
					} else if ("${place}" == "second" && item.owner == true) {

						//var d = new Date(item.time);
						//var myDate = dateFormat(d);
						var element = $('<li><div class="message-data"><span class="message-data-name"><i class="fa fa-circle you"></i>'
								+ item.user1.firstname
								+ '&nbsp;'
								+ item.user1.surname
								+ '</span></div><div class="message you-message">'
								+ item.text + '</div></li>');
						$(".chat-ul").prepend(element);
					}
				});
	}

	function getNewChats(actionurl, id, place) {
		
		$.ajax({
			type : "GET",
			url : actionurl,
			data : {
				'id' : id,
				'place' : place
				
			},
			success : function(data) {
				result = data;
				printChats(result);
			}
		});
	}
	
	$(document).ready(function() {
		getChats("${getChats}", "${id}");

		$("#btn-chat").click(function(event) {
			
			event.preventDefault();
			addChat();
		});
		
		setInterval(function(){getNewChats("${newChats}","${id}","${place}")},3000);
		
		
	});
</script>