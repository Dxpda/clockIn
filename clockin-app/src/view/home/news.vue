<template>
    <div>
        <div class="top">
          <van-tabs v-model="activeName" @click="getType">
            <van-tab title="我发布的"  name="iValue">
                <detail v-if="show" :isType="value"></detail>
            </van-tab>
            <van-tab title="我评价的" name="iAssess">
              <detail v-if="!show"  :isType="value"></detail>
            </van-tab>
          </van-tabs>
        </div>
    </div>
</template>

<script>
import request from "@/axios/index"
import cookie from "js-cookie"
import Detail from "@/view/home/detail";

export default {
  name: "Home",
  components: {Detail},
  data() {
    return{
      activeName:"iValue",
      value:null,
      show:true
    }
  },
  mounted() {
    this.getType()
  },
  methods:{
    getType(name,title){
      if (this.activeName === "iValue"){
          this.value = "add"
          this.show = true
      }else if (this.activeName === "iAssess"){
        this.value = "assess"
        this.show = false
      }
      // console.log(this.value)
      // console.log(name)
      // console.log(title)
    }

  }

}
</script>


<style scoped>

</style>
