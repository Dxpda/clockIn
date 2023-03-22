<template>
  <div class="root">
    <div class="top">
      <font size="5">欢迎使用考勤系统</font>

        <!-- 输入手机号，调起手机号键盘 -->
        <van-field class="input"  v-model="tel" type="tel" placeholder="请输入用户名" />
        <!-- 输入密码 -->
        <van-field class="input" v-model="password" type="password" placeholder="请输入密码" />

        <van-field
          v-if="!isLogin"
          class="input"
          v-model="sms"
          center
          clearable
          placeholder="请输入短信验证码"
        >
          <template #button>
            <van-button size="small" v-if="isButton" type="primary" style="border-radius: 20px;" @click="sms1">{{code}}</van-button>
            <van-button size="small" v-if="!isButton" type="primary" style="border-radius: 20px;" loading>{{code}}</van-button>
          </template>
        </van-field>


        <van-button v-if="isLogin && isButton" class="button" type="primary" @click="login">登录</van-button>
        <van-button v-if="isLogin && !isButton" class="button" type="primary" loading>登录</van-button>

        <van-button v-if="!isLogin && isButton" class="button" type="danger" @click="show = true">注册</van-button>
        <van-button v-if="!isLogin && !isButton" class="button" type="danger" loading>注册</van-button>
        <div style="margin-top: 10px;">
          <font size="3" color="blank" @click="isRegister" >
            {{ isLogin === false ? "已有账号?点击登录" : "没有账号?点击注册"}}
          </font>
        </div>
    </div>

    <van-popup v-model="show" round position="bottom" :style="{ height: '30%' }">
      <van-radio-group v-model="radio">
        <van-cell-group>
          <van-cell size="large" title="老师" clickable @click="radio = '1'">
            <template #right-icon>
              <van-radio name="1" />
            </template>
          </van-cell>
          <van-cell title="管理员" clickable @click="radio = '0'">
            <template #right-icon>
              <van-radio name="0" />
            </template>
          </van-cell>
        </van-cell-group>
      </van-radio-group>

        <van-button v-if="!isLogin && isButton" class="button register" type="danger" @click="register">注册</van-button>
        <van-button v-if="!isLogin && !isButton" class="button register" type="danger" loading>注册</van-button>

    </van-popup>


  </div>
</template>

<script>


import request from "@/axios/index"
import Cookie from 'js-cookie'

export default {
  name: "HLogin",
  data(){
    return{
      tel:null,
      password:null,
      isLogin:true,
      sms:null,
      code:"发送验证码",
      isButton:true,
      show:false,
      radio:2,
      isClick:true,
    }
  },
  mounted() {
    this.isLogins()
  },
  methods:{
    //登录
    login(){
      if(this.tel === "" || this.tel === null){
        this.$toast.fail("用户名输入有误!")
        return
      }
      if(this.password === "" || this.password === null  || this.password.length < 6){
        this.$toast.fail("密码输入有误!")
        return
      }
      this.isButton = false
      request({
        url:"/login",
        method:"post",
        data:{
          user:this.tel,
          password:this.password
        }
      }).then(res=>{
        if(res.status === 200){
          Cookie.set("user",res.data.user,{expires:2})
          Cookie.set("token",res.data.token,{ expires: 2 })
          this.$toast.success("登录成功!")
          this.$router.push("/index/home")
        }
        this.isButton = true

      })
    },

    //注册
    register(){

      if(this.tel === "" || this.tel === null){
        this.$toast.fail("用户名输入有误!")
        return
      }

      if(this.password === "" || this.password === null || this.password.length < 6 ){
        this.$toast.fail("密码输入有误!")
        return
      }
      if(this.sms === "" || this.sms === null){
        this.$toast.fail("验证码输入有误!")
        return
      }

      if(this.radio !== "0" && this.radio !== "1"){
        this.$toast.fail("请选择账号类型!")
        this.isButton = true
        return
      }

      this.isButton = false
      request({
        url:"/register",
        method:"post",
        data:{
          user:this.tel,
          password:this.password,
          sms:this.sms,
          type:this.radio
        }
      }).then(res=>{
        if(res.status === 200){
          this.$toast.success("注册成功!")
          this.isLogin = true
        }
        this.show = false
        this.code = "发送验证码"
        this.sms = null
        this.isButton = true
      })

    },
    //验证码
    sms1(){

      if(this.tel === "" || this.tel === null){
        this.$toast.fail("用户名输入有误!")
        return
      }
      if(this.password === "" || this.password === null || this.password.length < 6 ){
        this.$toast.fail("密码输入有误!")
        return
      }
      this.isButton = false
      request({
        url:"/sms",
        method:"post",
        data:{
          password:this.password,
          user:this.tel,
        }
      }).then(res=>{
        this.code = "发送成功"
        this.isButton = true
        this.$toast.success(res.data)
      })
    },
    //判断是登录还是注册
    isRegister(){
      if(this.isLogin){
        this.isLogin = false
      }else{
        this.isLogin = true
      }
    },
    //判断是否已经登录过,如果登录了,直接跳转到首页
    isLogins(){
      let token = Cookie.get("token")
      if (token !== undefined){
          this.$router.push("/index/home")
      }
    }
  }
}
</script>

<style scoped>
  .register{
    margin-left: 10%;
    position: relative;
    top: 25%;

  }
  .top{
    background-color: rgba(255, 192, 203, 0.048);
    width: 80%;
    height: 300px;
    position: absolute;
    top: 20%;
    margin-left: 10%;
    padding-top: 5px;
    box-shadow: 5px 3px 10px 4px rgba(117, 111, 111, 0.308);
    border-radius: 20px;
  }
  .input{
    width: 80%;
    margin-left: 10%;
    margin-top: 15px;
    background-color: rgba(57, 243, 181, 0.603);
    border-radius: 20px;
  }
  .top,.title{
    text-align: center;
    font-family: "楷体";
  }
  .title{
    font-size: 100px;
    color: rgb(6, 146, 240);
  }
  .button{
    width: 80%;
    border-radius: 20px;
    margin-top: 10px;
  }
</style>
