<template>
  <div>
    <router-view></router-view>
  </div>
</template>


<script>


import axios1 from"./axios/index"

export default {
  name: 'App',
  components: {

  },
  data(){
    return{

    }
  },
  methods:{
    aaa(){
      axios1({
        url:"/dada",
        method:"post",
        data:{
          name:"123132"
        }
      }).then(res=>{
        console.log(res);
      })
    }
  },
  mounted(){
    // this.aaa()
  }
}
</script>

<style>
  html{
    background-color: rgb(229, 250, 255);
  }
  .topHead{
        width: 100%;
        background-color: rgba(163, 161, 165, 0.7);
        height: 40px;
        text-align: center;
        line-height: 40px;
        font-family: "楷体";
        margin-bottom: 10px;
    }
</style>
