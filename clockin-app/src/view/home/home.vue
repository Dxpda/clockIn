<template>
  <div>
    <div class="root">
      <van-search style="margin-bottom: 5px; " v-model="value" @search="getValue" placeholder="根据教室号进行查询"/>
      <detail ref="detail"></detail>

    </div>
  </div>
</template>

<script>

import detail from "./detail"

export default {
  name: "Home",
  components: {
     detail,
  },
  data(){
    return{
      value:"",
    }
  },
  methods:{
    //查询指定班级
    getValue() {
        this.$refs.detail.$emit('getValue',this.value) // 方法1:触发监听事件
        this.$refs.detail.getValue() // 方法2:直接调用
    }
  },
  mounted() {
    this.getValue()
  }

}
</script>
<style scoped>
.root {
  width: 96%;
  margin-left: 2%;
  padding-top: 2px;

}
</style>

