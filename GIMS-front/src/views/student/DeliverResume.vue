<template>
  <div>
    <div v-show="isShow">
      <el-form :inline="true">
        <el-form-item>
          <el-input v-model="searchForm.employName" aria-placeholder="名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getEmployList">搜索</el-button>
        </el-form-item>
      </el-form>

      <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection"></el-table-column>
        <el-table-column prop="employId" label="招聘id"></el-table-column>
        <el-table-column prop="employName" label="招聘名称"></el-table-column>
        <el-table-column prop="companyName" label="公司名称"></el-table-column>
        <el-table-column prop="professionName" label="职业名称"></el-table-column>
        <el-table-column prop="wages" label="工资(/月)" :formatter="showWages"></el-table-column>
        <el-table-column prop="icon" label="操作">
          <template v-slot="scope">
            <template>
              <el-popconfirm title="确认投递吗?" @confirm="submitForm(scope.row)">
                <el-button type="primary" slot="reference">投递</el-button>
              </el-popconfirm>
            </template>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div v-show="!isShow" id="result">
      <el-result icon="success" title="成功提示" subTitle="请等待结果"></el-result>
    </div>

  </div>

</template>

<script>
export default {
  name: "DeliverResume",
  data() {
    return {
      searchForm: {
        employName: ''
      },
      multipleSelection: [],
      tableData: [],
      editForm: {
        studentId: undefined,
        employId: undefined,
        deliverStatus: 0
      },
      accountId: undefined,
      isShow: true
    }
  },
  created() {
    this.getEmployList()
    this.getUserInfo()
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
    getEmployList() {
      if (this.editForm.studentId !== undefined) {
        this.charge()
      }
      this.$axios.get('/employ/detail', {
        params: {
          employName: this.searchForm.employName,
        }
      }).then(res => {
        this.tableData = res.data.data
      })
    },
    submitForm(row) {
      this.editForm.employId = row.employId
      this.$axios.post('/deliver/add', this.editForm)
          .then(res => {
            this.$message({
              showClose: true,
              message: '操作成功',
              type: 'success',
              onClose: () => {
                this.editForm.employName = ''
                this.getEmployList()
              }
            });
          })
    },
    showWages(row) {
      if (row.wages === null) {
        return "工资面谈"
      } else {
        return row.wages
      }
    },
    getUserInfo() {
      this.$axios.get("/sys/userInfo").then(res => {
        this.accountId = res.data.data.accountId
        this.getStudentId()
      })
    },
    getStudentId() {
      this.$axios.post('student/getStudent', this.accountId).then(res => {
        this.editForm.studentId = res.data.data.studentId
        this.charge()
      })
    },
    charge() {
      this.$axios.post('deliver/getDeliver', this.editForm.studentId).then(res => {
        const list = res.data.data
        this.isShow = list.length === 0;
      })
    }
  }
}
</script>

<style>
.el-pagination {
  float: right;
  margin-top: 20px;
}

#result {
  position: relative;
  top: 50%;
  transform: translateY(50%);
  text-align: center;
}
</style>