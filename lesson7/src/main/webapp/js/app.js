window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

const getField = ($this, type, name) => $($this).find(type.toString() + "[name='" + name + "']").val();

const ajax = (action, data, successFunction) => {
    data["action"] = action;

    $.ajax({
        type: "POST",
        dataType: "json",
        data: data,
        success: function (response) {
            if (!successFunction(response)) {
                return false;
            }

            if (response["redirect"]) {
                location.href = response["redirect"];
            }
        }
    });
}