/**
 * student.html
 * 
 */

var vm = new Vue({
	el: "#app",
	data: {
		userid: null,
		username: null,
		title: "欢迎使用学生成绩管理系统",
		tree: [{
			text: "我的信息管理",
		}, {
			text: "班级信息查询",
		}, {
			text: "我的选课信息",
		}, {
			text: "请假申请",
		}, {
			text: "成绩信息查询",
		}, {
			text: "系统信息管理",
		}, ],
		// 表格头
		tabletitle: null,
		// 表格内容
		tablecontent: [],
		// 当前显示界面的标量
		menu: null,
		// 搜索关键词
		placeholder: "搜索",
		pwd:null,
		newpwd: null,
	},
	methods: {
		// 验证用户是否已登录
		validation() {
			axios.post("/student/validation").then(function(response) {
				if (response.data.code == -1) {
					location.href = "login.html"
					return
				}
				vm.userid = response.data.student.id
				vm.username = response.data.student.name
			})
		},
		// 创建目录
		showTree() {
			$('#tree').treeview({
				data: this.tree,
				levels: 0,
				showBorder: true,
				onhoverColor: "#c9f9ff",
				highlightSelected: true,
				collapseIcon: "glyphicon glyphicon-folder-open",
				expandIcon: "glyphicon glyphicon-folder-close",
				emptyIcon: "glyphicon glyphicon-list-alt",
				backColor: "#aae2f9",
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
						vm.tabletitle = ["学号", "姓名", "性别", "电话", "QQ", "班级"]
						break
					case 1:
						vm.placeholder = "班级名"
						vm.tabletitle = ["名称", "描述"]
						break
					case 2:
						vm.placeholder = "课程名"
						vm.tabletitle = ["名称", "授课老师", "上课时间", "已选人数", "最大可选人数", "描述"]
						break
					case 3:
						vm.tabletitle = ["请假人", "请假时间", "请假理由", "是否批准", "回复"]
						break
					case 4:
						vm.placeholder = "课程名"
						vm.tabletitle = ["学生", "课程名", "分数", "评价"]
						break
					case 5:
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
			axios({
				url: "/info/getPersonalInfo_Student",
				method: "post",
				params: {
					type: this.menu,
				}
			}).then(res=> (vm.tablecontent = res.data))
		},
		// 修改个人信息
		alterPersonalInfo() {
			axios({
				url: "op/alterPersonalInfo",
				method: "post",
				params: {
					type: 0,
					content: JSON.stringify(this.tablecontent)
				}
			}).then(function(res) {
				alert("成功修改" + res.data.success + "条数据;失败" + res.data.failure + "条");
				vm.getTableInfo(vm.menu)
			})
		},
		// 搜索
		searchKey(flag) {
			var option = null;
			if(flag==1){
				option={
						url: "/search/clazz",
						method: "post",
						params: {
							key: $("#key").val(),
						}
				}
			}else if(flag==2){
				option={
						url: "/search/select_course",
						method: "post",
						params: {
							key: $("#key").val(),
						}
				}
			}else{
				option={
						url: "/search/student/score_of_student",
						method: "post",
						params: {
							key: $("#key").val(),
						}
				}
			}
			
			axios(option).then(response => (vm.tablecontent = response.data))
		},
		// 修改密码
		alterPassword() {
			axios({
				url: "/op/alterPassword",
				method: "post",
				params: {
					pwd: this.pwd,
					newpwd:this.newpwd,
					type: 0
				}
			}).then(function(res) {
				if (res.data.code == 1) {
					alert("修改密码成功")
					vm.pwd = ""
					vm.newpwd = ""
				} else{
					alert("修改密码失败")
				}
			})
		},
		// 导出
		exportOp() {
			location.href = "/op/student/exportScore?id=" + this.userid;
		},
		exit() {
			axios.post("/exit").then(response => (location.href = "login.html"))
		},
	},
	mounted() {
		this.validation()
		this.showTree()
	}

})

var modal = new Vue({
	el: "#modal",
	data: {
		menu: null,
		// 已选课程列表
		selectedList: [],
		// 未选课程列表
		unSelectedList: [],
		// 想要选择的课程列表
		willselectList: [],
		// 想要退选的课程列表
		willUnselectList: [],
		// 请假信息
		leave_info:null,
		leave_time:null
	},
	methods: {
		// 获取选课相关信息
		alertSelect() {
			this.menu = vm.menu
			// 获取已选课程
			axios({
				url: "/info/getPersonalInfo_Student",
				method: "post",
				params: {
					type: 2,
					id: vm.userid
				}
			}).then(function(res) {
				modal.selectedList = res.data
			})
			// 获取未选课程
			axios({
				url: "/info/getPersonalInfo_Student",
				method: "post",
				params: {
					type: 5,
					id: vm.userid
				}
			}).then(function(res) {
				modal.unSelectedList = res.data
			})
		},
		// 提交选课结果
		subSelect() {
			axios({
				url: "/op/subSelect",
				method: "post",
				params: {
					id: vm.userid,
					willselectList: JSON.stringify(this.willselectList),
					willUnselectList: JSON.stringify(this.willUnselectList)
				}
			}).then(function(res) {
				modal.willselectList = []
				modal.willUnselectList = []
				alert("成功修改" + res.data.success + "条数据;失败" + res.data.failure + "条");
				vm.getTableInfo(vm.menu)
			})
		},
		// 请假申请
		addLeave() {
			if (this.leave_Info == "" || this.leave_time == "")
				return
			axios({
				url: "/op/student/addleave",
				method: "post",
				params: {
					info:this.leave_info,
					time:this.leave_time
				}
			}).then(function(res) {
				alert("成功请假" + res.data.success + "次;失败" + res.data.failure + "条");
				vm.getTableInfo(vm.menu)
			})
		}
	}
})
