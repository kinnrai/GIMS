<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.deliverId" aria-placeholder="投递编号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDeliverList">搜索</el-button>
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
      <el-table-column prop="deliverId" label="投递id"></el-table-column>
      <el-table-column prop="studentId" label="学生id"></el-table-column>
      <el-table-column prop="employId" label="招聘id"></el-table-column>
      <el-table-column prop="deliverStatus" label="招聘状态">
        <template v-slot="scope">
          <el-tag size="small" v-if="scope.row.deliverStatus===0" type="danger">未接受</el-tag>
          <el-tag size="small" v-else-if="scope.row.deliverStatus===1" type="success">已接受</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="icon" label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="handleEdit(scope.row.deliverId)">修改</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确认删除吗?" @confirm="handleDel(scope.row.deliverId)">
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
      <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px" deliver="demo-ruleForm">
        <el-form-item label="学生" prop="studentId" label-width="100px">
          <el-select v-model="editForm.studentId" placeholder="请选择学生" @click.native="getAllStudent">
            <el-option :label="student.studentName" :key="index" :value="student.studentId" v-for="(student,index) in allStudent"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="招聘" prop="employId" label-width="100px">
          <el-select v-model="editForm.employId" placeholder="请选择招聘" @click.native="getAllEmploy">
            <el-option :label="employ.employName" :key="index" :value="employ.employId" v-for="(employ,index) in allEmploy"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="deliverStatus" label-width="100px">
          <el-select v-model="editForm.deliverStatus" placeholder="请选择招聘" @click.native="getAllEmploy">
            <el-option label="未接受" :key="0" :value="0"></el-option>
            <el-option label="已接受" :key="1" :value="1"></el-option>
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
  name: "DeliverTable",
  data() {
    return {
      searchForm: {
        deliverId: undefined
      },
      delStatus: true,
      multipleSelection: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalPage: 1,
      dialogVisible: false,
      editForm: {
        deliverId: undefined,
        studentId: undefined,
        employId: undefined,
        deliverStatus: undefined
      },
      rules: {
        studentId: [
          {required: true, message: '请选择学生', trigger: 'change'}
        ],
        employId: [
          {required: true, message: '请选择招聘', trigger: 'change'}
        ],
        deliverStatus: [
          {required: true, message: '请选择状态', trigger: 'change'}
        ]
      },
      allStudent: [],
      allEmploy: []
    }
  },
  created() {
    this.getDeliverList()
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
      this.$axios.get('/deliver/info/' + id)
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
    handleSizeChange(val) {
      this.pageSize = val
      this.getDeliverList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getDeliverList()
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
      this.$axios.get('/deliver/list', {
        params: {
          deliverId: this.searchForm.deliverId,
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
          this.$axios.post('/deliver/' + (this.editForm.deliverId ? 'update' : 'add'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    this.editForm.deliverName = ''
                    this.getDeliverList()
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
    getAllStudent() {
      this.$axios.get('/student/getAllStudent')
          .then(res => {
            this.allStudent = res.data.data
          })
    },
    getAllEmploy() {
      this.$axios.get('/employ/getAllEmploy')
          .then(res => {
            this.allEmploy = res.data.data
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