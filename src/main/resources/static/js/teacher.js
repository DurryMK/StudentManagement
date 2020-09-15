/**
 * student.html
 * 
 */

var vm = new Vue({
	el: "#app",
	data: {
		userid: null,
		username: null,
		classid: null,
		leavenum: null,
		title: "欢迎使用学生成绩管理系统",
		tree: [{
			text: "我的信息管理",
		}, {
			text: "学生信息查询",
		}, {
			text: "请假审核",
		}, {
			text: "成绩信息管理",
		}, {
			text: "系统信息管理",
		}, ],
		// 表格头
		tabletitle: null,
		// 表格内容
		tablecontent: [],
		// 当前显示界面的标量
		menu: -1,
		// 搜索关键词
		key: null,

		// 密码修改
		pwd: null,
		newpwd: null,

		summarytitle: ["课程名", "最高分", "最低分", "平均分"],
		summary: [],
		// 要录入的文件
		importFile: null,
		
		cutButton:false
	},
	methods: {
		// 验证用户是否已登录
		validation() {
			axios.post("/teacher/validation").then(function(response) {
				if (response.data.code == -1) {
					location.href = "login.html"
					return
				}
				vm.userid = response.data.teacher.id
				vm.username = response.data.teacher.name
				vm.classid = response.data.teacher.clazz_id
			})
		},
		// 跳转到欢迎界面
		toWelcome() {
			this.title = "欢迎使用学生成绩管理系统"
			this.menu = -1
		},
		// 获取未审核的请假数量
		getLeaveInfoNum() {
			axios.post("/info/teacher/leaveInClassNum").then(res => (vm.leavenum = res.data))
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
						vm.tabletitle = ["工号", "姓名", "性别", "电话", "QQ", "班级"]
						break
					case 1:
						vm.tabletitle = ["学号", "姓名", "性别", "电话", "QQ"]
						break
					case 2:
						vm.tabletitle = ["请假人", "请假时间", "请假理由", "是否批准", "回复"]
						break
					case 3:
						vm.title = "成绩信息列表"
						vm.tabletitle = ["学号", "学生", "课程名", "分数", "评价"]
						break
					case 4:
						vm.tabletitle = ["修改密码"]
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
				url: "/info/getPersonalInfo_Teacher",
				method: "post",
				params: {
					type: this.menu
				}
			}
			axios(option).then(res => (vm.tablecontent = res.data))
		},
		// 搜索
		searchKey() {
			axios({
				url: "/search/teacher/studentInClass",
				method: "post",
				params: {
					key: $("#key").val(),
				}
			}).then(response => (vm.tablecontent = response.data))
		},
		// 修改个人信息
		alterPersonalInfo() {
			axios({
				url: "op/alterPersonalInfo",
				method: "post",
				params: {
					type: 1,
					content: JSON.stringify(this.tablecontent)
				}
			}).then(function(res) {
				alert("成功修改" + res.data.success + "条数据;失败" + res.data.failure + "条");
				vm.getTableInfo(vm.menu)
			})
		},
		// 导出学生信息
		exportStudentInfo() {
			location.href = "/op/teacher/exportStudentInfo?id=" + this.userid + "&cid=" + this.classid;
		},
		// 导出学生成绩
		exportOp_studentScore() {
			if(this.cutButton)
				location.href = "/op/teacher/exportSummary";
			else
				location.href = "/op/teacher/exportStudentScore";
				
		},
		// 录入学生成绩
		importOp_studentScore() {
			var formData = new FormData() // 声明一个FormData对象
			var formData = new window.FormData() // vue 中使用
			formData.append('file', document.querySelector('input[type=file]').files[0])
			var options = { // 设置axios的参数
				url: '/op/teacher/importStudentScore',
				data: formData,
				method: 'post',
				headers: {
					'Content-Type': 'multipart/form-data'
				}
			}
			axios(options).then(function(res) {
				var code = res.data.code
				if (code == -1) {
					alert("文件录入出错！")
				} else if (code == 0) {
					alert("文件内容为空")
				} else {
					alert("成功录入" + res.data.success + "条数据;失败" + res.data.failure + "条")
				}
				vm.getTableInfo(vm.menu)
			})
		},
		
		// 修改密码
		alterPassword() {
			var option = {
				url: "/op/alterPassword",
				method: "post",
				params: {
					pwd: this.pwd,
					newpwd: this.newpwd,
					type: 1
				}
			}
			axios(option).then(function(res) {
				if (res.data.code == 1) {
					alert("修改密码成功!")
					vm.newpwd = ""
					vm.pwd = ""
				} else if (res.data.code == 0) {
					alert("密码错误!")
				} else {
					alert("修改密码失败!")

				}
			})
		},
		// 请假批复
		replay(flag, id) {
			if (!confirm("该操作将无法撤回,是否继续？"))
				return
			axios({
				url: "/op/teacher/replay",
				method: "post",
				params: {
					flag: flag,
					content: $("#" + id).val(),
					id: id
				}
			}).then(function(res) {
				vm.getTableInfo(vm.menu)
			})
		},
		// 导出学生成绩
		exportOp() {
			location.href = "/op/teacher/exportScore";
		},
		// 切换显示成绩列表和成绩统计 并且获取成绩统计信息
		showScore(flag) {
			//表格内容切换
			this.menu = flag;
			//切换按钮
			this.cutButton = !this.cutButton
			//切换标题
			if(this.cutButton)
				this.title = "成绩信息统计"
			else
				this.title = "成绩信息列表"
			var option = {
				url: "/info/getPersonalInfo_Teacher",
				method: "post",
				params: {
					type: 4,
				}
			}
			axios(option).then(res => (vm.summary = res.data))
		},
		exit() {
			axios.post("/exit").then(response => (location.href = "login.html"))
		},
	},
	mounted() {
		this.validation()
		this.getLeaveInfoNum()
		this.showTree()
	}
})
