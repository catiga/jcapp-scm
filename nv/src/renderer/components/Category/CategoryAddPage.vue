<template>
    <div class="content-page">
        <div class="content-nav">
            <el-breadcrumb class="breadcrumb" separator="/">
                <el-breadcrumb-item :to="{ name: 'nature' }">商品设置</el-breadcrumb-item>
                <el-breadcrumb-item>{{infoForm.id ? '编辑分类' : '添加分类'}}</el-breadcrumb-item>
            </el-breadcrumb>
            <div class="operation-nav">
                <el-button type="primary" @click="goBackPage" icon="arrow-left">返回列表</el-button>
            </div>
        </div>
        <div class="content-main">
            <div class="form-table-box">
                <el-form ref="infoForm" :rules="infoRules" :model="infoForm" label-width="120px">
                    <el-form-item label="上级分类" prop="name">
                        <el-select v-model="infoForm.parent_id" placeholder="请选择上级分类">
                            <el-option v-for="item in parentCategory" :key="item.id" :label="item.name"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="分类名称" prop="name">
                        <el-input v-model="infoForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="简短介绍">
                        <el-input type="textarea" v-model="infoForm.front_name" :rows="1"></el-input>
                        <div class="form-tip"></div>
                    </el-form-item>
                    <el-form-item label="分类图片" prop="img_url">
                        <img v-if="infoForm.img_url" :src="url + infoForm.img_url" class="image-show">
                        <el-upload
                                class="upload-demo"
                                name="file"
                                accept="image/jpeg,image/gif,image/png,image/jpg"
                                :action="uploadAction"
                                :on-remove="bannerRemove"
                                :before-remove="beforeBannerRemove"
                                :file-list="fileList"
                                :on-success="handleUploadBannerSuccess"
                                :data="picData"
                                :before-upload="checkFile"
                        >
                            <el-button v-if="!infoForm.img_url" size="small" type="primary">点击上传</el-button>
                        </el-upload>
                        <div class="form-tip">图片尺寸：顶级分类为690*自定义, 只能上传jpg/png文件</div>
                    </el-form-item>
                    <el-form-item label="分类图片高度" prop="name">
                        <el-input v-model="infoForm.p_height"></el-input>
                    </el-form-item>
                    <el-form-item label="图标" prop="icon_url">
                        <img v-if="infoForm.icon_url" :src="url + infoForm.icon_url" class="image-show">
                        <el-upload
                                class="upload-demo"
                                name="file"
                                accept="image/jpeg,image/gif,image/png,image/jpg"
                                :action="uploadAction"
                                :on-remove="iconRemove"
                                :before-remove="beforeIconRemove"
                                :file-list="fileList2"
                                :data="picData"
                                :on-success="handleUploadIconSuccess"
                                :before-upload="checkFile"
                        >
                            <el-button v-if="!infoForm.icon_url" size="small" type="primary">点击上传</el-button>
                        </el-upload>

                        <div class="form-tip">图片尺寸：图标250*250, 只能上传jpg/png文件</div>
                    </el-form-item>
                    <el-form-item label="排序">
                        <el-input-number v-model="infoForm.sort_order" :min="1" :max="1000"></el-input-number>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmitInfo">确定保存</el-button>
                        <el-button @click="goBackPage">取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    import api from '@/config/api';
    import ElFormItem from "../../../../node_modules/element-ui/packages/form/src/form-item.vue";
    import qs from 'qs'
    
    export default {
        data() {
            return {
                root: '',
                qiniuZone:'',
                uploadAction:'',
                fileList: [],
                fileList2: [],
                uploaderHeader: {
                    'X-Nideshop-Token': localStorage.getItem('token') || '',
                },
                parentCategory: [
                    {
                        id: 0,
                        name: '顶级分类'
                    }
                ],
                infoForm: {
                    id: 0,
                    name: "",
                    parent_id: 0,
                    front_name: '',
                    img_url: '',
                    sort_order: 100,
                    icon_url:''
                    // is_show: true,
                },
                infoRules: {
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'},
                    ],
                    front_name: [
                        {required: true, message: '请输入简介', trigger: 'blur'},
                    ],
                    img_url: [
                        {required: true, message: '请选择分类图片', trigger: 'blur'},
                    ],
                    icon_url: [
                        {required: true, message: '请选择分类图标', trigger: 'blur'},
                    ],
                },
                picData: {
                    token: '',
                    uc: 'cat'
                },
                url: ''
            }
        },
        methods: {
            getQiniuToken() {
                let that = this
                this.axios.post('index/getQiniuToken').then((response) => {
                    let resInfo = response.data.data;
                    console.log(resInfo);
                    that.picData.token = resInfo.token;
                    that.url = resInfo.url;
                })
            },
            checkFile(file) {
      			//let isIMAGE = file.type === 'image/jpeg'||'image/gif'||'image/png';
      			let isIMAGE = file.type.indexOf('image')>-1?true:false;
      			let isLt1M = file.size / 1024 / 1024 < 1;

      			if (!isIMAGE) {
        			this.$message.error('上传文件只能是图片格式!');
        			return;
      			}
      			if (!isLt1M) {
        			this.$message.error('上传文件大小不能超过 1MB!');
        			return;
      			}
      			return isIMAGE && isLt1M;
      			
    		},
            beforeBannerRemove(file, fileList) {
                return this.$confirm(`确定移除该图？删除后将无法找回`);
            },
            beforeIconRemove(file, fileList) {
                return this.$confirm(`确定移除图标？删除后将无法找回`);
            },
            bannerRemove(file, fileList) {
                this.infoForm.img_url = '';
                let id = this.infoForm.id;
                this.axios.post(this.root + 'category/deleteBannerImage', qs.stringify({id: id})).then((response) => {
                    this.$message({
                        type: 'success',
                        message: '删除成功'
                    });
                });
            },
            iconRemove(file, fileList) {
                this.infoForm.icon_url = '';
                let id = this.infoForm.id;
                this.axios.post(this.root + 'category/deleteIconImage', qs.stringify({id: id})).then((response) => {
                    this.$message({
                        type: 'success',
                        message: '删除成功'
                    });
                });
            },
            goBackPage() {
                this.$router.go(-1);
            },
            onSubmitInfo() {
                this.infoForm.level = this.infoForm.parent_id == 0 ? 'L1' : 'L2';
                console.log(this.infoForm.level);
                this.$refs['infoForm'].validate((valid) => {
                    if (valid) {
                        this.axios.post(this.root + 'category/store', qs.stringify(this.infoForm)).then((response) => {
                            if (response.data.errno === 0) {
                                this.$message({
                                    type: 'success',
                                    message: '保存成功'
                                });
                                this.$router.go(-1)
                            } else {
                                this.$message({
                                    type: 'error',
                                    message: '保存失败'
                                })
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
            handleUploadBannerSuccess(res, file) {
                let url = this.url;
                this.infoForm.img_url = res.data[0];
            },
            handleUploadIconSuccess(res, file) {
                let url = this.url;
                this.infoForm.icon_url = res.data[0];
            },
            getTopCategory() {
                this.axios.get(this.root + 'category/topCategory?id=' + this.infoForm.id).then((response) => {
                    this.parentCategory = this.parentCategory.concat(response.data.data);
                })
            },
            getInfo() {
            	/*
                if (this.infoForm.id <= 0) {
                    return false
                }
                */
                //加载分类详情
                let that = this;
                this.axios.post(this.root + 'category/info', qs.stringify({id: that.infoForm.id})).then((response) => {
                	if(resInfo!=null) {
                		let resInfo = response.data.cat;
	                    console.log(resInfo);
	                    let data = {
	                        name: '分类图',
	                        url: resInfo.img_url
	                    };
	                    this.fileList.push(data);
	                    let iconData = {
	                        name: '图标',
	                        url: resInfo.icon_url
	                    };
	                    this.fileList2.push(iconData);
	                    that.infoForm = resInfo;
                	}
                	that.url = resInfo.prefix;
                })
            }

        },
        components: {ElFormItem},
        mounted() {
        	this.root = api.rootUrl;
            this.infoForm.id = this.$route.query.id || 0;
            this.getTopCategory();
            this.getInfo();
            this.uploadAction = api.rootUrl + 'common/upload';
            //this.url = 'http://e.local:8080/img_server/';
            //this.qiniuZone = api.qiniu;
            //this.getQiniuToken();
        }
    }

</script>

<style scoped>
    .image-uploader {
        height: 105px;
    }

    .image-uploader .el-upload {
        border: 1px solid #d9d9d9;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .image-uploader .el-upload:hover {
        border-color: #20a0ff;
    }

    .image-uploader .image-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        min-width: 105px;
        height: 105px;
        line-height: 105px;
        text-align: center;
    }

    .image-show {
        background-color: #f8f8f8;
        min-width: 105px;
        height: 105px;
        display: block;
    }

</style>
