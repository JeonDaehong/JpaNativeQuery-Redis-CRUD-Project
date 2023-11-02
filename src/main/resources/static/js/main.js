$(document).ready(function() {
    $("#logoutButton").click(function() {
        $.post("/user/logout", function (data, status) {
            if (status === "success") {
                window.location.href = "/"; // 성공 시 메인 페이지로 리디렉션
            } else {
                alert.error("로그아웃 요청 실패");
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error("오류 발생: " + errorThrown);
        });
    });
});

function getBoardList() {

}

function plzLoginAlert() {
    alert("로그인 후에 이용가능합니다.");
}