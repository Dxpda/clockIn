import {compress, compressAccurately} from 'image-conversion';
//base64转file,单独使用时可以使用这个!
function dataURLtoFile(dataurl,filename){
    let arr = dataurl.split(","),mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),n = bstr.length,u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr],filename,{type:mime});
}
//把图片文件作为参数传递到方法中
function beforeAvatarUpload(file) {
    return new Promise((resolve) => {
        // 压缩到1024KB,这里的1024就是要压缩的大小,可自定义
        compressAccurately(file, 300).then(res => {
            resolve(res);
        });
        //compressAccurately有多个参数时传入对象
        //imageConversion.compressAccurately(file, {
        // size: 1024, //图片大小压缩到1024kb
        // width:1280 //宽度压缩到1280
        //}).then(res => {
        //resolve(res)
        //})
    })
}






//结尾处将该方法暴露出来供外部调用
export{
    dataURLtoFile,
    beforeAvatarUpload

}
