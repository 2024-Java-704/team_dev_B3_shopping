		function onCheckCreditRadioButton() {
			if (document.getElementById("credit").checked) {
				document.getElementById("creditCardContainer1").style.visibility = "visible"
				document.getElementById("creditCardContainer2").style.visibility = "visible"
				document.getElementById("creditCardContainer3").style.visibility = "visible"
				document.getElementById("creditCardContainer4").style.visibility = "visible"
			} else {
				document.getElementById("creditCardContainer1").style.visibility = "collapse"
				document.getElementById("creditCardContainer2").style.visibility = "collapse"
				document.getElementById("creditCardContainer3").style.visibility = "collapse"
				document.getElementById("creditCardContainer4").style.visibility = "collapse"
			}
		}
		
//document.getElementById("form").addEventListener("submit", function(event){
//		var credit = document.getElementById('credit').value.trim();
//		if(credit == ""){
//			alert("クレジットカード情報を入力してください");
//			event.preventDefault();
//		}
//	});