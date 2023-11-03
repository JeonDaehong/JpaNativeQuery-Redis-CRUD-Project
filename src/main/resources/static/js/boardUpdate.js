function goBack() {
    window.history.back();
}

$(document).ready(function() {

    $("#updateButton").click(function() {

        let boardIdVal = $("#boardId").val();
        let titleVal = $("#title").val();
        let contentVal = $("#content").val();
        let userIdVal = $("#userId").val();

        if ( titleVal === "" || contentVal === "" ) {
            alert("빈 곳 없이 채워주세요.");
            return;
        }

        if (!confirm("정말로 수정하시겠습니까?")) return;

        let data = {
            title : titleVal,
            content : contentVal,
            boardId : boardIdVal
        };

        $.ajax({
            type : "POST", // 또는 다른 HTTP 메소드 사용
            url : "/board/update",
            data : data,
            success : function(responseCode) {
                if (responseCode === "1000") {
                    alert("수정 성공");
                    location.href = "/boardInfoPage?userId="+userIdVal+"&boardId="+boardIdVal;
                } else if (responseCode === "-1000") {
                    alert("시스템 에러가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
                    location.href = "/boardInfoPage?userId="+userIdVal+"&boardId="+boardIdVal;
                }
            },
            error: function(xhr, status, error) {
                // 에러 처리
                alert.error("에러:", error);
            }
        });
    });
});