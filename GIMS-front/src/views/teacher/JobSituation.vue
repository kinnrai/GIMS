<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-select v-model="currentClassId">
          <el-option :label="item.className" :key="index" :value="item.classId" v-for="(item,index) in ownClass" @click.native="onSwitchChange"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.studentName" aria-placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="onSwitchChange">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-switch
            v-model="isEmployed"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="已就业"
            inactive-text="未就业"
            @click.native="onSwitchChange">
        </el-switch>
      </el-form-item>
      <el-form-item>
        {{ employedNum }}/{{ unemployedNum + employedNum }}
      </el-form-item>
    </el-form>

    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="studentId" label="学生id"></el-table-column>
      <el-table-column prop="studentName" label="学生名"></el-table-column>
    </el-table>

  </div>
</template>

<script>
export default {
  name: "LookStudent",
  data() {
    return {
      searchForm: {
        studentName: ''
      },
      multipleSelection: [],
      tableData: [],
      allClass: [],
      ownClass: [],
      currentClassId: undefined,
      isEmployed: true,
      employedNum: undefined,
      unemployedNum: undefined
    }
  },
  created() {
    this.getAccountId()
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    onSwitchChange() {
      if (this.isEmployed) {
        this.getEmployed()
      } else {
        this.getUnemployed()
      }
    },
    getEmployed() {
      this.$axios.get('/student/employed', {
        params: {
          name: this.searchForm.studentName,
          classId: this.currentClassId
        }
      }).then(res => {
        this.tableData = res.data.data
        this.employedNum = res.data.data.length
      })
    },
    getUnemployed() {
      this.$axios.get('/student/unemployed', {
        params: {
          name: this.searchForm.studentName,
          classId: this.currentClassId
        }
      }).then(res => {
        this.tableData = res.data.data
        this.unemployedNum = res.data.data.length
      })
    },
    getAllClass() {
      this.$axios.get('/class/getAllClass')
          .then(res => {
            this.allClass = res.data.data
          })
    },
    getAccountId() {
      this.$axios.get("/sys/userInfo").then(res => {
        this.accountId = res.data.data.accountId
        this.getOwnClass()
      })
    },
    getOwnClass() {
      this.$axios.post('/class/ownClass', this.accountId).then(res => {
        this.ownClass = res.data.data
        this.currentClassId = this.ownClass[0].classId
        this.getEmployed()
      })
    }
  }
}
</script>

<style>

</style>