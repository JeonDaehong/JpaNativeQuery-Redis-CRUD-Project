function goBack() {
    location.href = "/";
}

function getBoardInfo(userId, boardId) {
    location.href = "/boardInfoPage?userId="+userId+"&boardId="+boardId;
}

$(document).ready(function() {
    $("#beforePageButton").click(function() {
        let startIdx = $("#startIdx").val();
        let page = $("#page").val();
        let pagingType = 2;
        location.href = "/boardListPage?startIdx="+startIdx+"&page="+page+"&pagingType="+pagingType;
    });
    $("#nextPageButton").click(function() {
        let startIdx = $("#startIdx").val();
        let page = $("#page").val();
        let pagingType = 1;
        location.href = "/boardListPage?startIdx="+startIdx+"&page="+page+"&pagingType="+pagingType;
    });
});