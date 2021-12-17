function showDeleteConfirmModal(link, entityName) {
    entityId = link.attr("entityId")
    $("#deleteButton").attr("href", link.attr("href"));
    $("#confirmText").text("Are you sure you want to delete this " + entityName + " ID: " + entityId + "");
    $("#user-modal-dialog").modal();
}
