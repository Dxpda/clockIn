<template>

  <div style="width: 100%">
    <van-notice-bar
        left-icon="volume-o"
        :text="data === null ?'暂未查询到验证码' : data.value"
    />
    <div style="width: 100%;text-align: center">
      <font size="4" color="red" >请输入注册时输入的账号与密码</font>
      <van-field v-model="text" label="账号" />
      <van-field v-model="password" type="password" label="密码" />
      <van-button v-if="show" type="info" round @click="sms" style="width: 90%;">搜索</van-button>
      <van-button v-if="!show" loading round type="info" loading-text="搜索中..." style="width: 90%;" />
    </div>
  </div>
</template>

<script>
import request from "@/axios/index"
export default {
  name: "sms",
  data(){
    return{
      text:null,
      password:null,
      show:true,
      data:null
    }
  },
  methods:{
    sms(){
      console.log(this.data)
      if (this.text === null || this.text === ""){
        this.$toast.fail("用户名输入有误")
        return;
      }
      if(this.password === "" || this.password === null || this.password.length < 6 ){
        this.$toast.fail("密码输入有误!")
        return
      }
      this.show = false
      request({
        url:"getsms",
        method:"post",
        data:{
          user:this.text,
          password:this.password
        }
      }).then(res=>{
        if (res.status === 200){
          this.data = res.data
        }
        this.show = true
      })

    }
  }
}
</script>

<style scoped>

</style>
