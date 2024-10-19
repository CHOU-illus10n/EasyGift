function formatNumber(n){
	n = n.toString();
	return n[1] ? n : '0'+n;
}

function getDate(time){
	// 将2002-06-20 00:00:00 转换成 2002-06-20
	time = time.split(" ");
	var res = time[0].split("-");
	return res[0]+'-'+formatNumber(res[1])+'-'+formatNumber(res[2]);
}

function getTime(time){
	time = time.split(" ");
	var res = time[1].split(':');
	return formatNumber(res[0]) + ":" + formatNumber(res[1]);
}

function getImgUrlList(url){
	let list = url.split('|');
	return list.splice(0, list.length-1);
}

function getIndex(giftId, obj) {
	for (let index = 0; index < obj.length; index++) {
		const element = obj[index];
		if (element.goodTypeId == giftId) {
			return index;
		}
	}
}

export const GetTime = getTime;
export const GetDate = getDate;
export const GetImgUrlList = getImgUrlList;
export const GetIndex = getIndex;