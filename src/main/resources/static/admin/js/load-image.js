$(document).ready(function () {
    $("#buttonCancel").on("click", function () {
        window.location = "[[@{/users}]]"
    })

    $("#fileImage").change(function () {
        showImageThumbnail(this);
    });
});

function showImageThumbnail(fileInput) {
    const file = fileInput.files[0];
    const reader = new FileReader();
    reader.onload = function (event) {
        $("#thumbnail").attr("src", event.target.result);
    };
    reader.readAsDataURL(file);
}