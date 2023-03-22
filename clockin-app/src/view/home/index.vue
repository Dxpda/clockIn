<template>
  <div>
    <router-view></router-view>
    <van-tabbar placeholder v-model="active">
      <van-tabbar-item to="/index/home" icon="home-o">主页</van-tabbar-item>
      <van-tabbar-item to="/index/news" icon="friends-o">消息</van-tabbar-item>
      <van-tabbar-item to="/index/add" icon="edit">发布</van-tabbar-item>
<!--      <van-tabbar-item to="/index/history" icon="clock-o">历史</van-tabbar-item>-->
      <van-tabbar-item icon="share-o" @click="logout">退出登录</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import Cookie from "js-cookie"
import {Dialog} from 'vant';

export default {
  data() {
    return {
      value: null,
      active: 0,
      onActive: 0
    }
  },
  watch: {
    active(_, out) {
      this.onActive = out
    }
  },
  methods: {

    logout() {
      Dialog.confirm({
        title: '退出',
        message: '真的要退出吗?',
      })
          .then(() => {
            Cookie.remove("token")
            Cookie.remove("user")
            this.$router.push("/")
          })
          .catch(() => {
            this.active = this.onActive
          });
    },

    isActive(){
      let a = this.$route.path
      if (a === "/index/home"){
          this.active = 0
      }else  if (a === "/index/news"){
        this.active = 1
      }else if (a === "/index/add"){
        this.active = 2
      }else if (a === "/index/history"){
        this.active = 3
      }
    }

  },
  mounted() {
    this.isActive()

  }

}
</script>

<style>

</style>
