<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.foundId" aria-placeholder="开设编号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getFoundList">搜索</el-button>
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
      <el-table-column prop="foundId" label="开设id"></el-table-column>
      <el-table-column prop="schoolId" label="学校id"></el-table-column>
      <el-table-column prop="majorId" label="专业id"></el-table-column>
      <el-table-column prop="icon" label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="handleEdit(scope.row.foundId)">修改</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确认删除吗?" @confirm="handleDel(scope.row.foundId)">
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
      <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px" found="demo-ruleForm">
        <el-form-item label="学校" prop="schoolId" label-width="100px">
          <el-select v-model="editForm.schoolId" placeholder="请选择学校" @click.native="getAllSchool">
            <el-option :label="school.schoolName" :key="index" :value="school.schoolId" v-for="(school,index) in allSchool"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" prop="majorId" label-width="100px">
          <el-select v-model="editForm.majorId" placeholder="请选择专业" @click.native="getAllMajor">
            <el-option :label="major.majorName" :key="index" :value="major.majorId" v-for="(major,index) in allMajor"></el-option>
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
  name: "FoundTable",
  data() {
    return {
      searchForm: {
        foundId: undefined
      },
      delStatus: true,
      multipleSelection: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalPage: 1,
      dialogVisible: false,
      editForm: {
        foundId: undefined,
        schoolId: undefined,
        majorId: undefined
      },
      rules: {
        schoolId: [
          {required: true, message: '请选择学校', trigger: 'change'}
        ],
        majorId: [
          {required: true, message: '请选择专业', trigger: 'change'}
        ]
      },
      allSchool: [],
      allMajor: []
    }
  },
  created() {
    this.getFoundList()
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
      this.$axios.get('/found/info/' + id)
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
          ids.push(row.foundId)
        })
      }
      this.$axios.post('/found/delete', ids)
          .then(res => {
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success',
              onClose: () => {
                this.getFoundList()
              }
            })
          })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getFoundList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getFoundList()
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
    getFoundList() {
      this.$axios.get('/found/list', {
        params: {
          foundId: this.searchForm.foundId,
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
          this.$axios.post('/found/' + (this.editForm.foundId ? 'update' : 'add'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    this.editForm.foundName = ''
                    this.getFoundList()
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
    getAllSchool() {
      this.$axios.get('/school/getAllSchool')
          .then(res => {
            this.allSchool = res.data.data
          })
    },
    getAllMajor() {
      this.$axios.get('/major/getAllMajor')
          .then(res => {
            this.allMajor = res.data.data
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