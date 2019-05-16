$(document).ready(function () {

    $("#regform").submit(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });

});

function delete_ajax_submit() {
	var url_id = "/users/" + $('#id').val()
    console.log(url_id);
    $.ajax({
        type: "DELETE",
        url: "/users",
        data: json,
        dataType: 'text', // json -> text
        success: function (result) {
        	console.log("SUCCESS : ", data);
        	window.location.href = "/disjoin.html";
        },
        error: function (e) {
        	console.log ("ERROR : ", e);
        	}
		});
	}
function update_ajax_submit() {
	var data = {
			"name" : $('name').val(),
			"company" : $('#company').val();
	};
	var user_id = "/users/" + $('#id').val();
	var json = JSON.stringify(data);
    console.log(json);
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: user_id,
        data: json,
        dataType: 'text', // json -> text
        cache :
        success: function (result) {
        	console.log("SUCCESS : ", data);
        	window.location.href = "/disjoin.html";
        },
        error: function (e) {
        	console.log ("ERROR : ", e);
        	}
		});
	}