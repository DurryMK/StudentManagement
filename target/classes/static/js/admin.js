/**
 * admin.html
 * 
 */

var vm = new Vue({
	el: "#app",
	data: {
		userid: null,
		username: null,
		title: "学生成绩管理系统",
		tree: [{
			text: "学生信息管理",
		}, {
			text: "教师信息管理",
		}, {
			text: "班级信息管理",
		}, {
			text: "课程信息管理",
		}, {
			text: "选课信息管理",
		}, {
			text: "成绩信息管理",
		}, {
			text: "系统信息管理",
		}, ],

		tabletitle: null,
		tablecontent: [],

		placeholder: "搜索",
		key: null,
		menu: -1,

		deleteList: [],
		selectDelete: [],

		pwd: null,
		newpwd: null,

		help: "以下对象将无法被删除:【已有选课、班级的学生/授课教师/已被选择的课程/已有学生的班级】",

		addData: {
			student_id: "",
			sex: "",
			mobile: "",
			course_id: "",
			coursename: "",
			clazz_id: "",
			clazzname: "",
			teacher_d: "",
			name: "",
			time: "",
			max: "",
			score: "",
			remark: "",
		},
		alterOp_: false,

		allstudent: [],
		allclazz: [],
		allcourse: [],
		allteacher: [],

		studentname: null,
		coursename: null,
		teachername: null,
		clazzname: null,


	},
	methods: {
		// 验证用户是否已登录
		validation() {
			axios.post("/admin/validation").then(function(response) {
				if (response.data.code == -1) {
					location.href = "login.html"
					return
				}
				vm.userid = response.data.admin.id
				vm.username = response.data.admin.name
			})
		},
		// 创建目录
		showTree() {
			$('#tree').treeview({
				data: this.tree,
				levels: 0,
				showBorder: true,
				onhoverColor: "#66CCFF",
				highlightSelected: true,
				collapseIcon: "glyphicon glyphicon-folder-open",
				expandIcon: "glyphicon glyphicon-folder-close",
				emptyIcon: "glyphicon glyphicon-list-alt",
				backColor: "#66FFFF",
				searchResultColor: "#FF9900"
			})
			this.createClick()
		},
		// 创造Tree的点击事件监听
		createClick() {
			$('#tree').on('nodeSelected', function(event, data) {
				vm.title = data.text
				vm.menu = data.nodeId
				switch (data.nodeId) {
					case 0:
						vm.placeholder = "输入学号/姓名"
						vm.tabletitle = ["学号", "姓名", "性别", "电话", "QQ", "班级"]
						break
					case 1:
						vm.placeholder = "输入工号/姓名"
						vm.tabletitle = ["工号", "姓名", "性别", "电话", "QQ", "班级"]
						break
					case 2:
						vm.placeholder = "输入班级名称"
						vm.tabletitle = ["名称", "描述"]
						break
					case 3:
						vm.placeholder = "输入课程名"
						vm.tabletitle = ["名称", "授课老师", "上课时间", "已选人数", "最大可选人数", "描述"]
						break
					case 4:
						vm.placeholder = "输入学生姓名"
						vm.tabletitle = ["学生", "课程名"]
						break
					case 5:
						vm.placeholder = "输入学生姓名"
						vm.tabletitle = ["学生", "课程名", "分数", "评价"]
						break
					case 6:
						vm.tabletitle = ["修改密码", ""]
						break
					default:
						break
				}
				vm.getTableInfo();
			})
		},
		// 获取表格内容
		getTableInfo() {
			var option = {
				url: "/info/singleTableInfo",
				method: "post",
				params: {
					type: this.menu
				}
			}
			axios(option).then(res => (vm.tablecontent = res.data))
		},
		// 搜索
		searchKey() {
			var option = {
				url: "/info/admin/search",
				method: "post",
				params: {
					key: $("#key").val(),
					type: this.menu
				}
			}
			axios(option).then(res => (vm.tablecontent = res.data))
		},
		// 获取可删除的列表
		deleteOp() {
			if (this.menu == null)
				return
			axios({
				url: "/info/admin/getDeleteList",
				method: "post",
				params: {
					type: this.menu
				}
			}).then(response => (this.deleteList = response.data))
		},
		// 删除操作
		deleteOp_() {
			if (this.selectDelete.length == 0)
				return
			if (!confirm("该操作将无法撤回,是否继续？"))
				return
			axios({
				url: "/op/admin/delete",
				method: "post",
				params: {
					type: this.menu,
					list: JSON.stringify(this.selectDelete)
				}
			}).then(function(res) {
				alert("成功删除" + res.data.success + "条数据;失败" + res.data.failure + "条");
				vm.getTableInfo(vm.menu)
				vm.selectDelete = []
			})
			this.deleteOp()
		},
		addOp() {
			for (var i = 0; i < 4; i++) {
				var option = {
					url: "/info/singleTableInfo",
					method: "post",
					params: {
						type: i
					}
				}
				if (i == 0)
					axios(option).then(res => (vm.allstudent = res.data))
				if (i == 1)
					axios(option).then(res => (vm.allteacher = res.data))
				if (i == 2)
					axios(option).then(res => (vm.allclazz = res.data))
				if (i == 3)
					axios(option).then(res => (vm.allcourse = res.data))
			}
		},
		// 添加操作
		addOp_() {
			if (this.menu == null)
				return
			console.log(this.addData)
			axios({
				url: "/op/admin/add",
				method: "post",
				params: {
					type: this.menu,
					list: JSON.stringify(this.addData)
				}
			}).then(function(res) {
				if (res.data.code == 0) {
					alert("操作成功！")
				} else {
					alert("操作失败！")
				}
				vm.clear()
				vm.getTableInfo(vm.menu)
			})
		},
		//修改操作
		alterOp() {
			this.alterOp_ = !this.alterOp_
			/*if (!confirm("是否保存当前页面信息？"))
				return
			axios().then(function(res) {
				alert("成功修改" + res.data.success + "条数据;失败" + res.data.failure + "条");
				vm.getTableInfo(vm.menu)
			})*/
		},
		//保存修改
		save(index) {
			var option = {
				url: "op/admin/alterInfo",
				method: "post",
				params: {
					type: this.menu,
					content: JSON.stringify(this.tablecontent[index])
				}
			}
			axios(option).then(function(res) {
				if (res.data.code != 1) {
					alert("保存数据失败")
				} else {
					alert("已更新数据")
				}
				vm.getTableInfo(vm.menu)
			})
		},
		clear() {
			this.addData.student_id = ""
			this.addData.sex = ""
			this.addData.mobile = ""
			this.addData.course_id = ""
			this.addData.coursename = ""
			this.addData.clazz_id = ""
			this.addData.clazzname = ""
			this.addData.teacher_d = ""
			this.addData.name = ""
			this.addData.time = ""
			this.addData.max = ""
			this.addData.score = ""
			this.addData.remark = ""
			this.studentname = ""
			this.clazzname = ""
			this.coursename = ""
			this.teachername = ""

		},
		showSelect(flag, item) {
			if (flag == 0)
				this.studentname = item
			if (flag == 1)
				this.clazzname = item
			if (flag == 2)
				this.coursename = item
			if (flag == 3)
				this.teachername = item
		},
		// 修改密码
		alterPassword() {
			axios({
				url: "/op/alterPassword",
				method: "post",
				params: {
					pwd: this.pwd,
					newpwd: this.newpwd,
					type: 2
				}
			}).then(function(res) {
				if (res.data.code == -1) {
					alert("修改密码失败")
				} else if (res.data.code == 1) {
					alert("修改密码成功")
					vm.newpwd = ""
				} else {
					alert("新密码不能与近期使用密码相同")
				}
			})
		},
		exit() {
			axios.post("/exit").then(response => (location.href = "login.html"))
		},
	},
	created() {
		this.validation()
	},
	mounted() {

		this.showTree()
	}
})
