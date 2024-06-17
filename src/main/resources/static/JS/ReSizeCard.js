// HTMLから要素を取得
const inputElements = document.getElementsByClassName("ViewCard");

// 各要素にイベントリスナーを追加
for (let i = 0; i < inputElements.length; i++){
	const inputElement = inputElements[i];
	inputElement.addEventListener("change", roadImg, false);
	console.log("動いているよ");
}

// 画像の読み込み
function roadImg(e) {
  const file = this.files[0];
  let reader = new FileReader();
  reader.onload = () => {
    const imgData = reader.result;
    const resizedImgData = resizeImg(imgData);
    const image = document.getElementsByClassName("studentCard");
    for (let i = 0; i < image.length; i++) {
		const thisImage = image[i];
		thisImage.src = resizedImgData;
	}
  };
  reader.readAsDataURL(file);
}

// リサイズ
function resizeImg(imgData) {
  const canvas = document.createElement('canvas');
  canvas.width = 10;
  canvas.height = 10;
  const ctx = canvas.getContext('2d');
  const img = new Image();
  img.onload = () => {
    ctx.drawImage(img, 0, 0, 10, 10);
  };
  img.src = imgData;
  return canvas.toDataURL('image/png');
}
