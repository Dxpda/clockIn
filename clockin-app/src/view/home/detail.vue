<template>
  <div>
    <div v-if="list.length === 0">
      <van-empty description="暂无考勤"/>
    </div>
    <div v-for="(item,index) in list" :key="item.id">
      <van-cell
          clickable
          center
          round
          :title="item.createTime"
          :value="'应到:'+item.maxPerson+'实到:'+item.actualPerson"
          :label="item.uname+'🍧'+item.type+'发布了🌳'+ item.uclass+'班的考勤'"
          @click="getDetail(item.id)"
      >
        <!-- 使用 right-icon 插槽来自定义右侧图标 -->
        <template #right-icon>
          <van-icon name="arrow"/>
        </template>
        <template #icon>
          <div style="color: darkgrey">
            {{ index + 1 }} &nbsp
          </div>

        </template>
      </van-cell>
    </div>
    <div v-if="list.length >= 10*limit2" style="text-align: center; margin-top: 10px;">
      <font size="3" color="darkgray" @click="clicks">点击加载更多🌻</font>
    </div>

    <div>
      <van-popup v-model="popUpShow" position="top" :style="{ height: '60%' }">
        <van-cell-group>
          <div v-if="isUpdate  && isButton">
            <van-field input-align="center" readonly v-model="detail.uclass" label="班级"/>
            <van-field input-align="center" readonly v-model="detail.floor" label="楼栋"/>
            <van-field input-align="center" readonly v-model="detail.maxPerson" label="应到人数"/>
            <van-field input-align="center" readonly v-model="detail.actualPerson" label="实到人数"/>
            <van-field
                input-align="center"
                readonly
                v-model="detail.mark"
                rows="1"
                autosize
                label="备注"
                type="textarea"
            />
          </div>
          <div v-if="!isUpdate">
            <van-field input-align="center" v-model="detail.uclass" label="班级"/>
            <van-field input-align="center" v-model="detail.floor" label="楼栋"/>
            <van-field input-align="center" v-model="detail.maxPerson" label="应到人数"/>
            <van-field input-align="center" v-model="detail.actualPerson" label="实到人数"/>
            <van-field
                input-align="center"
                v-model="detail.mark"
                rows="1"
                autosize
                label="备注"
                type="textarea"
            />
          </div>

          <div v-if="!isButton">
            <van-field v-model="success.score" input-align="center" type="digit" label="评分"/>
            <van-field
                input-align="center"
                v-model="success.mark"
                rows="1"
                autosize
                label="备注"
                type="textarea"
            />
          </div>

        </van-cell-group>
        <van-uploader v-model="fileList" :show-upload="false" :deletable="false"/>
        <div style="width: 100%">
          <van-button v-if="isUpdate" type="danger" style="width: 50%" round @click="deletes">删除</van-button>
          <van-button v-if="!isUpdate" type="info" style="width: 50%" round @click="isUpdate = true">取消修改</van-button>

          <van-button v-if="isUpdate" type="warning" style="width: 50%" round @click="isUpdate = false">修改</van-button>
          <van-button v-if="!isUpdate" type="warning" style="width: 50%" round @click="update">确认修改</van-button>
        </div>

        <div style="width: 100%;margin-top: 10px">
          <van-button v-if="isButton" type="primary" style="width: 90%;margin-left: 5%;margin-bottom: 20px" round
                      @click="isButton = false">评价
          </van-button>
          <van-button
              v-if="!isButton"
              type="primary"
              style="width: 90%;margin-left: 5%;margin-bottom: 20px"
              @click="onAssess"
              round
          >立即评价
          </van-button>
        </div>
      </van-popup>
    </div>
  </div>
</template>

<script>
import request from "@/axios/index"
import cookie from "js-cookie"
export default {
  name: "detail",
  props:{
    isType:String
  },

  data() {
    return {
      isUpdate: true,
      type: this.isType,
      isButton: true,
      value: "",
      limit: 10,
      limit2: 1,
      show: false,
      popUpShow: false,
      list: [],
      detail: {},
      image: [],
      fileList: [],
      success: {}
    }
  },
  watch: {
    isUpdate(newVal, _) {
      if (!newVal) {
        this.isButton = true
      }
    },
    isButton(newVal, _) {
      if (!newVal) {
        this.isUpdate = true
      }
    },
    isType(newVal,_){
      this.getList()
    },
    popUpShow(newVal, _) {
      if (!newVal) {
        this.isUpdate = true
        this.isButton = true
      }
    }

  },
  mounted() {
    this.getValue1()
    this.getType()
  },
  methods: {
    //评价
    onAssess() {
      request({
        url: "assess",
        method: "post",
        data: {
          score: this.success.score,
          mark: this.success.mark,
          id: this.detail.id,
        }
      }).then(res => {
        if (res.status === 200) {
          this.$toast.success("评价成功")
        }
        this.popUpShow = false
      })
      this.isButton = false
    },
    //删除
    deletes() {
      this.$toast.loading({
        message: '加载中...',
        deletes: 0,
        forbidClick: true,
      });
      request({
        url: "detail",
        method: "delete",
        data: {
          id: this.detail.id,
        }
      }).then(res => {
        if (res.status === 200) {
          this.$toast.success("删除成功!")
          this.isUpdate = true
          this.getList();
          this.popUpShow = false
        }
        this.$toast.clear()
      })


    },
    //修改
    update() {
      this.$toast.loading({
        message: '加载中...',
        deletes: 0,
        forbidClick: true,
      });
      request({
        url: "detail",
        method: "put",
        data: {
          uclass: this.detail.uclass,
          id: this.detail.id,
          mark: this.detail.mark,
          maxPerson: this.detail.maxPerson,
          actualPerson: this.detail.actualPerson,
          floor: this.detail.floor
        }
      }).then(res => {
        if (res.status === 200) {
          this.$toast.success("修改成功!")
          this.isUpdate = true
        }
        this.$toast.clear()
      })
    },
    //详情
    getDetail(id) {
      this.isUpdate = true
      this.fileList = []
      this.detail = {}
      this.popUpShow = true
      request({
        url: "/getDetail",
        method: "post",
        data: {
          id
        }
      }).then(res => {
        console.log();
        this.detail = res.data
        if (res.data.image !== "") {
          for (const t of res.data.image.split(",")) {
            this.fileList.push({url: this.$baseApi + t})
          }
        }
      })
    },
    //初始刷新
    getList() {
      if (this.list.length === 10 * this.limit2) {
        this.limit = this.limit + 10
        this.limit2 = this.limit2 + 1
        this.show = true
      }
      request({
        url: "/getList?page=1&limit=" + this.limit + "&value=" + this.value+"&type="+this.type,
        method: "get",
      }).then(res => {
        this.list = res.data
        console.log(this.list)
      })
    },
    //加载更多
    clicks() {
      this.getList()
    },
    //查询指定班级
    getValue1() {
      this.$on('getValue', (res) => {
          this.value = res
      })
    },
    getValue(){
      this.getList()
    },
    getType(){
      this.getList()
    }

  }
}
</script>


<style scoped>

</style>
