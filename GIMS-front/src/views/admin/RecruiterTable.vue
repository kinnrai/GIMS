<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.recruiterName" aria-placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getRecruiterList">搜索</el-button>
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
      <el-table-column prop="recruiterId" label="招聘者id"></el-table-column>
      <el-table-column prop="recruiterName" label="招聘者名"></el-table-column>
      <el-table-column prop="companyId" label="公司id"></el-table-column>
      <el-table-column prop="accountId" label="账号id"></el-table-column>
      <el-table-column prop="icon" label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="handleEdit(scope.row.recruiterId)">修改</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确认删除吗?" @confirm="handleDel(scope.row.recruiterId)">
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
        <el-form-item label="招聘者名" prop="recruiterName" label-width="100px">
          <el-input v-model="editForm.recruiterName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="公司" prop="companyId" label-width="100px">
          <el-select v-model="editForm.companyId" placeholder="请选择公司" @click.native="getAllCompany">
            <el-option :label="company.companyName" :key="index" :value="company.companyId" v-for="(company,index) in allCompany"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账号" prop="accountId" label-width="100px">
          <el-select v-model="editForm.accountId" placeholder="请选择账号id" @click.native="getAccountNotUse">
            <el-option :label="accountId" :key="index" :value="accountId" v-for="(accountId,index) in accountNotUse"></el-option>
          </el-select>
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
  name: "RecruiterTable",
  data() {
    return {
      searchForm: {
        recruiterName: ''
      },
      delStatus: true,
      multipleSelection: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalPage: 1,
      dialogVisible: false,
      editForm: {
        recruiterId: 0,
        recruiterName: '',
        companyId: undefined,
        accountId: undefined
      },
      rules: {
        recruiterName: [
          {required: true, message: '请输入招聘者名', trigger: 'blur'}
        ],
        companyId: [
          {required: true, message: '请选择公司', trigger: 'change'}
        ],
        accountId: [
          {required: true, message: '请选择账号id', trigger: 'change'}
        ]
      },
      allCompany: [],
      accountNotUse: []
    }
  },
  created() {
    this.getRecruiterList()
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
      this.$axios.get('/recruiter/info/' + id)
          .then(res => {
            this.editForm = res.data.data
            this.dialogVisible = true
          })
    },
    handleDel(id) {
      const ids = []
      if (id !== null) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.recruiterId)
        })
      }
      this.$axios.post('/recruiter/delete', ids)
          .then(res => {
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success',
              onClose: () => {
                this.getRecruiterList()
              }
            })
          })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getRecruiterList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getRecruiterList()
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
    getRecruiterList() {
      this.$axios.get('/recruiter/list', {
        params: {
          recruiterName: this.searchForm.recruiterName,
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
          this.$axios.post('/recruiter/' + (this.editForm.recruiterId ? 'update' : 'add'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    this.editForm.recruiterName = ''
                    this.getRecruiterList()
                  }
                });
                this.editForm.accountId = undefined
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
    getAllCompany() {
      this.$axios.get('/company/getAllCompany')
          .then(res => {
            this.allCompany = res.data.data
          })
    },
    getAccountNotUse() {
      this.$axios.get('/account/getAccountNotUse')
          .then(res => {
            this.accountNotUse = res.data.data
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