// 期限日セルの取得
var auctionCheckCells = document.getElementsByClassName('listingStatus');

// 期限日に近い場合は色変えとクラス追加（縁取り用）
for (var i = 0; i < auctionCheckCells.length; i++) {
    if (listingStatusCheck(auctionCheckCells[i])) {
        auctionCheckCells[i].style.backgroundColor = '#ff4500';
        auctionCheckCells[i].classList.add("ended");
    }
    
}
    
// 期限が近いかどうかをチェック
function listingStatusCheck(auctionStatus) {

    var status = auctionStatus.textContent;

    // 確認用コンソール（正しく処理されているか）
    
    if(status === "終了"){
		return true;
	} else{
		return false;
	}

}