function goBack() {
    location.href='/boardListPage';
}

$(document).ready(function() {

    let boardIdVal = $("#boardId").val();

    $("#updateButton").click(function() {
        location.href = "/boardUpdatePage?boardId="+boardIdVal;
    });

    $("#deleteButton").click(function() {

        if (!confirm("정말로 글을 삭제하시겠습니까?")) return;

        let data = {
            boardId : boardIdVal
        };

        $.ajax({
            type : "POST", // 또는 다른 HTTP 메소드 사용
            url : "/board/delete",
            data : data,
            success : function(responseCode) {
                if (responseCode === "1000") {
                    alert("삭제 성공");
                    location.href = "/boardListPage";
                } else if (responseCode === "-1000") {
                    alert("시스템 에러가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
                    location.href = "/boardListPage";
                }
            },
            error: function(xhr, status, error) {
                // 에러 처리
                alert.error("에러:", error);
            }
        });

    });

});