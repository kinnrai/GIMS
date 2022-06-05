<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.username" aria-placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getAccountList">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="dialogVisible=true">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="确认删除吗?" @confirm="handleDel(null)">
          <el-button type="danger" slot="reference" :disabled="delStatus">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="accountId" label="账号id"></el-table-column>
      <el-table-column prop="username" label="账号名"></el-table-column>
      <el-table-column prop="avatar" label="头像">
        <template v-slot="scope">
          <el-avatar :src="scope.row.avatar" size="medium"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="typeId" label="账号类型">
        <template v-slot="scope">
          <el-tag size="small" v-if="scope.row.typeId===0" type="success">学生</el-tag>
          <el-tag size="small" v-else-if="scope.row.typeId===1" type="danger">教师</el-tag>
          <el-tag size="small" v-else-if="scope.row.typeId===2" type="danger">招聘者</el-tag>
          <el-tag size="small" v-else-if="scope.row.typeId===3" type="danger">管理员</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column prop="updateTime" label="修改时间"></el-table-column>
      <el-table-column prop="icon" label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="handleEdit(scope.row.accountId)">重置密码</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确认删除吗?" @confirm="handleDel(scope.row.accountId)">
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageSize"
        :total="totalPage">
    </el-pagination>

    <el-dialog title="提示" :visible.sync="dialogVisible" width="600px" :before-close="handleClose">
      <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号名" prop="username" label-width="100px">
          <el-input v-model="editForm.username" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" label-width="100px">
          <el-input v-model="editForm.email" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('editForm')">立即创建</el-button>
          <el-button @click="resetForm('editForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "AccountTable",
  data() {
    return {
      searchForm: {
        username: ''
      },
      delStatus: true,
      multipleSelection: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalPage: 1,
      dialogVisible: false,
      editForm: {
        accountId: 0,
        username: '',
        password: '',
        email: '',
      },
      rules: {
        username: [
          {required: true, message: '请输入账号名', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.getAccountList()
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
    handleEdit(id) {
      this.$axios.get('/account/info/' + id)
          .then(res => {
            this.editForm = res.data.data
            this.resetPassword()
          })
    },
    handleDel(id) {
      const ids = []
      if (id !== null) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.accountId)
        })
      }
      this.$axios.post('/account/delete', ids)
          .then(res => {
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success',
              onClose: () => {
                this.getAccountList()
              }
            })
          })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getAccountList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getAccountList()
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.editForm={};
            done();
          })
          .catch(_ => {
          });
    },
    getAccountList() {
      this.$axios.get('/account/list', {
        params: {
          username: this.searchForm.username,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data.data.records
        this.currentPage = res.data.data.current
        this.pageSize = res.data.data.size
        this.totalPage = res.data.data.total
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/account/add', this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    this.getAccountList()
                  }
                });
                this.dialogVisible = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    resetPassword() {
      this.editForm.password = 123456
      this.$axios.post('/account/update', this.editForm)
          .then(res => {
            this.$message({
              showClose: true,
              message: '密码已重置为123456',
              type: 'success',
              onClose:()=>{
                this.editForm={}
              }
            })
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