<template>
  <div style="height: 60%">
    <div class="topHead">
      {{ title }}
    </div>
    <div>
      <van-row>
        <van-col span="12">
          <van-field v-model="uClass" type="digit" placeholder="班级"/>
        </van-col>
        <van-col span="12">
          <van-field v-model="floor" type="digit" placeholder="楼栋"/>
        </van-col>
      </van-row>

      <van-row>
        <van-col span="12">
          <van-field v-model="maxPerson" type="digit" placeholder="应到人数"/>
        </van-col>
        <van-col span="12">
          <van-field v-model="actualPerson" type="digit" placeholder="实到人数"/>
        </van-col>
      </van-row>
      <van-field
          v-model="make"
          rows="2"
          autosize
          type="textarea"
          maxlength="100"
          placeholder="请输入留言"
          show-word-limit
      />
      <van-uploader
          v-model="fileList"
          multiple
          :max-count="6"
          :max-size="1024 * 1024*3"
          accept=".jpg,.png,.gif,.jpeg,.bmp"
          @oversize="onOversize"
          :before-read="beforeRead"
          :after-read="afterRead"
          :deletable="false"
      />
      <br>
    </div>

    <div class="bottom">
      <van-button v-if="isButtom" type="primary" class="button" round @click="submit">提交</van-button>
      <van-button v-if="!isButtom" type="primary" class="button" round disabled loading >提交</van-button>
    </div>

  </div>
</template>

<script>
import request from "@/axios/index"
import {beforeAvatarUpload} from "@/image/image"


export default {
  data() {
    return {
      title: "",
      fileList: [],
      image:[],

      maxPerson: null,
      actualPerson: null,
      uClass: null,
      floor: null,
      make: null,

      isButtom: true

    }
  },

  methods: {


    submit() {
      if (this.uClass === null || this.uClass === "") {
        this.$toast.fail("班级输入有误")
        return;
      }
      if (this.maxPerson === null || this.maxPerson === "") {
        this.$toast.fail("应到人数输入有误")
        return;
      }
      if (this.actualPerson === null || this.actualPerson === "") {
        this.$toast.fail("实到人数输入有误")
        return;
      }
      if (this.floor === null || this.floor === "") {
        this.$toast.fail("楼栋输入有误")
        return;
      }
      this.isButtom = false
      request({
        url: "/submit",
        method: "post",
        data: {
          uclass: this.uClass,
          maxPerson: this.maxPerson,
          actualPerson: this.actualPerson,
          floor: this.floor,
          mark: this.make,
          image:this.image.toString()
        }
      }).then(res=>{
        if (res.status === 200){
          this.$notity({ type: 'success', message: '提交成功' });
          this.maxPerson = null
          this.actualPerson = null
          this.uClass = null
          this.floor=null
          this.make = null
          this.fileList = []
        }
        this.isButtom = true
      })

    },

    //图片上传
    afterRead(file) {
      this.$toast.loading({
        message: '加载中...',
        duration:0,
        forbidClick: true,
      });
      this.isButtom = false
      let formData = new FormData()
      let file1 = beforeAvatarUpload(file.file)
      file1.then(res=>{
        let files = new File([res], file.file.name, {type: res.type})
        formData.append("file", files)
        request({
          url: "uploads",
          method: "post",
          data: formData,
          headers: {
            // 表示上传的是文件,而不是普通的表单数据
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => {
          if (res.status === 200){
            this.image.push(res.data)
          }
          // 手动清除 Toast
          this.$toast.clear();
          this.isButtom = true
        })
      })
    },


    onOversize(file) {
      this.$toast.fail('文件大小不能超过3MB');
    },

    beforeRead(file) {
      if (file.type === 'image/jpeg' || file.type === "image/png" && file.type !== "image/gif" && file.type !== "image/bmp") {
        return true
      } else {
        this.$toast.fail('格式不正确!');
        return false;
      }

    }
  },
  mounted() {
    this.title = this.$route.meta.title;
  },

}
</script>

<style scoped>
.button {
  width: 80%;
  margin-left: 10%;
}

.bottom {
  position: fixed;
  top: 80%;
  width: 100%;
}
</style>
