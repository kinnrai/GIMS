<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-popconfirm title="确认拒绝吗?" @confirm="handleDel(null)">
          <el-button type="danger" slot="reference" :disabled="delStatus">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="employName" label="招聘名"></el-table-column>
      <el-table-column prop="studentName" label="姓名"></el-table-column>
      <el-table-column prop="student" label="性别">
        <template v-slot="scope">
          <el-tag size="small" v-if="scope.row.sex===0" type="danger">男</el-tag>
          <el-tag size="small" v-else-if="scope.row.sex===1" type="success">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="schoolName" label="学校名"></el-table-column>
      <el-table-column prop="majorName" label="专业"></el-table-column>
      <el-table-column prop="credit" label="学分"></el-table-column>
      <el-table-column prop="resume" label="简历"></el-table-column>

      <el-table-column prop="icon" label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="submitForm(scope.row.deliverId)">接受</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确认拒绝吗?" @confirm="handleDel(scope.row.deliverId)">
              <el-button type="text" slot="reference">拒绝</el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>


  </div>
</template>

<script>
export default {
  name: "LookResume",
  data() {
    return {
      delStatus: true,
      multipleSelection: [],
      tableData: [],
      belongCompanyId: undefined
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
      this.delStatus = (val.length === 0);
    },
    handleDel(id) {
      const ids = []
      if (id !== null) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.deliverId)
        })
      }
      this.$axios.post('/deliver/delete', ids)
          .then(res => {
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success',
              onClose: () => {
                this.getDeliverList()
              }
            })
          })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.editForm = {};
            done();
          })
          .catch(_ => {
          });
    },
    getDeliverList() {
      this.$axios.post('/deliver/detail', this.belongCompanyId).then(res => {
        this.tableData = res.data.data
      })
    },
    submitForm(id) {
      this.$axios.post('/deliver/access', id)
          .then(res => {
            this.$message({
              showClose: true,
              message: '操作成功',
              type: 'success',
              onClose: () => {
                this.getDeliverList()
              }
            });
          })
    },
    getAccountId() {
      this.$axios.get("/sys/userInfo").then(res => {
        this.accountId = res.data.data.accountId
        this.getBelongCompany()
      })
    },
    getBelongCompany() {
      this.$axios.post("/company/getBelongCompany", this.accountId).then(res => {
        this.belongCompanyId = res.data.data
        this.getDeliverList()
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
</style>