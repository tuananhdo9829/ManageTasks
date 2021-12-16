function showDeleteConfirmModal(link) {
    userId = link.attr("userId")
    $("#deleteButton").attr("href", link.attr("href"));
    $("#confirmText").text("Are you sure you want to delete this ID " + userId + "?");
    $("#user-invite-modal").modal();
}
