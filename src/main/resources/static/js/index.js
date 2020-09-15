/**
 * 
 */
var vm = new Vue({
		el : "#app",
		data : {
			username:"a",
			pwd:"a",
			type:null,
			result:null,
		},
		methods : {
			subop(){
				if(this.username==null  ||  this.pwd==null||   this.type==null){
					this.showresult("请填写完整的信息!");return 
				}
				axios({
					url:"/index/login",
					method:"post",
					params:{
						username:this.username,
						pwd:this.pwd,
						type:this.type
					}
				}).then(function(response){
					if(response.data.code==-1){
						vm.showresult("用户信息错误!")
						return 
					}
					location.href=response.data.html
				})
			},
			showresult(msg){
				this.result=msg
			},
			out(){
				this.result = null
			}
		},
	})