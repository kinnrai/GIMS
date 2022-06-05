<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.className" aria-placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getClassList">搜索</el-button>
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
      <el-table-column prop="classId" label="班级id"></el-table-column>
      <el-table-column prop="className" label="班级名"></el-table-column>
      <el-table-column prop="teacherId" label="教师id"></el-table-column>
      <el-table-column prop="majorId" label="专业id"></el-table-column>
      <el-table-column prop="icon" label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="handleEdit(scope.row.classId)">修改</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确认删除吗?" @confirm="handleDel(scope.row.classId)">
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
        <el-form-item label="班级名" prop="className" label-width="100px">
          <el-input v-model="editForm.className" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="教师" prop="teacherId" label-width="100px">
          <el-select v-model="editForm.teacherId" placeholder="请选择教师" @click.native="getAllTeacher">
            <el-option :label="teacher.teacherName" :key="index" :value="teacher.teacherId" v-for="(teacher,index) in allTeacher"></el-option>
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
  name: "ClassTable",
  data() {
    return {
      searchForm: {
        className: ''
      },
      delStatus: true,
      multipleSelection: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalPage: 1,
      dialogVisible: false,
      editForm: {
        classId: undefined,
        className: '',
        teacherId: undefined,
        majorId: undefined
      },
      rules: {
        className: [
          {required: true, message: '请输入班级名', trigger: 'blur'}
        ],
        teacherId: [
          {required: true, message: '请选择教师', trigger: 'change'}
        ],
        majorId: [
          {required: true, message: '请选择专业', trigger: 'change'}
        ]
      },
      allTeacher: [],
      allMajor: []
    }
  },
  created() {
    this.getClassList()
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
      this.$axios.get('/class/info/' + id)
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
          ids.push(row.classId)
        })
      }
      this.$axios.post('/class/delete', ids)
          .then(res => {
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success',
              onClose: () => {
                this.getClassList()
              }
            })
          })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getClassList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getClassList()
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
    getClassList() {
      this.$axios.get('/class/list', {
        params: {
          className: this.searchForm.className,
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
          this.$axios.post('/class/' + (this.editForm.classId ? 'update' : 'add'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    this.editForm.className = ''
                    this.getClassList()
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
    getAllTeacher() {
      this.$axios.get('/teacher/getAllTeacher')
          .then(res => {
            this.allTeacher = res.data.data
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