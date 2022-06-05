<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.employName" aria-placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getEmployList">搜索</el-button>
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
      <el-table-column prop="employId" label="招聘id"></el-table-column>
      <el-table-column prop="employName" label="招聘名称"></el-table-column>
      <el-table-column prop="companyId" label="公司id"></el-table-column>
      <el-table-column prop="professionId" label="职业id"></el-table-column>
      <el-table-column prop="wages" label="工资(/月)" :formatter="showWages"></el-table-column>
      <el-table-column prop="icon" label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="handleEdit(scope.row.employId)">修改</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确认删除吗?" @confirm="handleDel(scope.row.employId)">
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
      <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px" employ="demo-ruleForm">
        <el-form-item label="招聘名" prop="employName" label-width="100px">
          <el-input v-model="editForm.employName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="公司" prop="companyId" label-width="100px">
          <el-select v-model="editForm.companyId" placeholder="请选择公司" @click.native="getAllCompany">
            <el-option :label="company.companyName" :key="index" :value="company.companyId" v-for="(company,index) in allCompany"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职业" prop="professionId" label-width="100px">
          <el-select v-model="editForm.professionId" placeholder="请选择职业" @click.native="getAllProfession">
            <el-option :label="profession.professionName" :key="index" :value="profession.professionId" v-for="(profession,index) in allProfession"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工资" prop="wages" label-width="100px">
          <el-input v-model.number="editForm.wages" auto-complete="off"></el-input>
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
  name: "EmployTable",
  data() {
    return {
      searchForm: {
        employName: ''
      },
      delStatus: true,
      multipleSelection: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalPage: 1,
      dialogVisible: false,
      editForm: {
        employId: undefined,
        employName: '',
        companyId: undefined,
        professionId: undefined,
        wages: undefined
      },
      rules: {
        employName: [
          {required: true, message: '请输入招聘名', trigger: 'blur'}
        ],
        companyId: [
          {required: true, message: '请选择公司', trigger: 'change'}
        ],
        professionId: [
          {required: true, message: '请选择职业', trigger: 'change'}
        ],
        wages: [
          {type: 'number', message: '请输入数字'}
        ]
      },
      allCompany: [],
      allProfession: []
    }
  },
  created() {
    this.getEmployList()
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
      this.$axios.get('/employ/info/' + id)
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
          ids.push(row.employId)
        })
      }
      this.$axios.post('/employ/delete', ids)
          .then(res => {
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success',
              onClose: () => {
                this.getEmployList()
              }
            })
          })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getEmployList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getEmployList()
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
    getEmployList() {
      this.$axios.get('/employ/list', {
        params: {
          employName: this.searchForm.employName,
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
          this.$axios.post('/employ/' + (this.editForm.employId ? 'update' : 'add'), this.editForm)
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
    getAllProfession() {
      this.$axios.get('/profession/getAllProfession')
          .then(res => {
            this.allProfession = res.data.data
          })
    },
    showWages(row) {
      if (row.wages === null) {
        return "工资面谈"
      } else {
        return row.wages
      }
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