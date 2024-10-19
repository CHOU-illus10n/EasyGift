function structMapping(data){
	let res = [];
	data.forEach(element => {
		let one = {};
		one.value = element.communityId;
		one.text = element.communityName;
		res = [...res, one];
	});
	return res;
}

function giftTypeMapping(data){
	let res = [{value: "0", text: "全部类型"}];
	data.forEach(element => {
		let one = {};
		one.value = element.goodTypeId;
		one.text = element.goodTypeName;
		res = [...res, one];
	})
	return res;
}


function setIsReceiver(list, isReceiver){
	list.forEach(element => {
		element.isReceiver = isReceiver;
	});
	return list;
}

function setIsReceive(list, isReceive){
	list.forEach(element => {
		element.isReceive = isReceive;
	});
	return list;
}

function strToTime(str) {
	str = str.replace(/-/g, '/');
	let res = new Date(str);
	return res;
}

function giftStatusMapping(data) {
	let res = [];
	if (!data) {
		return ;
	}
	data.forEach(item => {
		if(item.status == 0) {
			item.status = "审核中"
		}else if(item.status == 1) {
			item.status = "审核通过上架了"
		}else if(item.status == 2) {
			item.status = "审核不通过";
		}else if(item.status == 3) {
			item.status = "进行中"
		}
		res = [...res, item]
	})
	return res
}

export const StructMap = structMapping
export const GiftTypeMap = giftTypeMapping
export const SetIsReceiver = setIsReceiver
export const SetIsReceive = setIsReceive
export const GiftStatusMap = giftStatusMapping
export const StrToTime = strToTime
