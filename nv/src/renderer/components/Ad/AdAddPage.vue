<template>
    <div class="content-page">
        <div class="content-nav">
            <el-breadcrumb class="breadcrumb" separator="/">
                <el-breadcrumb-item :to="{ name: 'ad' }">广告列表</el-breadcrumb-item>
                <el-breadcrumb-item>{{infoForm.id ? '编辑广告' : '添加广告'}}</el-breadcrumb-item>
            </el-breadcrumb>
            <div class="operation-nav">
                <!--<el-button type="primary" @click="test" icon="arrow-left">test</el-button>-->
                <el-button type="primary" @click="goBackPage" icon="arrow-left">返回列表</el-button>
            </div>
        </div>
        <div class="content-main">
            <div class="form-table-box">
                <el-form ref="infoForm" :rules="infoRules" :model="infoForm" label-width="120px">
                	<el-form-item label="广告名称" prop="name">
                        <el-input v-model="infoForm.title"></el-input>
                    </el-form-item>
                    <el-form-item label="广告简介" prop="goods_brief">
                        <el-input type="textarea" v-model="infoForm.info" :rows="3"></el-input>
                        <div class="form-tip"></div>
                    </el-form-item>
                    
                    <el-form-item label="广告图片" prop="image_url">
                        <img v-if="infoForm.image_url" :src="url + infoForm.image_url" class="image-show">
                        <el-upload
                                class="upload-demo"
                                name="file"
                                accept="image/jpeg,image/gif,image/png,image/jpg"
                                :action="uploadAction"
                                :on-remove="adRemove"
                                :before-remove="beforeAdRemove"
                                :file-list="fileList"
                                :on-success="handleUploadImageSuccess"
                                :data="picData"
                                :before-upload="checkFile"
                        >
                            <el-button v-if="!infoForm.image_url" size="small" type="primary">点击上传</el-button>
                        </el-upload>
                        <div class="form-tip">图片尺寸：750*440</div>
                    </el-form-item>
                    <el-form-item label="跳转类型">
                        <el-radio-group v-model="infoForm.link_type">
                        	<el-radio :label="'00'">默认</el-radio>
                            <el-radio :label="'10'">商品跳转</el-radio>
                            <el-radio :label="'20'">链接跳转</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    
                    <el-form-item label="广告外部链接" prop="link" v-if="infoForm.link_type == '20'">
                        <el-input class="link-input" v-model="infoForm.link"></el-input>
                    </el-form-item>
                    <el-form-item label="商品id" prop="link" v-if="infoForm.link_type == '10'">
                        <el-input class="id-input" v-model="infoForm.goods_id"></el-input>
                        <el-popover
                                placement="right"
                                v-model="related_pop"
                        >
                            <el-table :data="chooseRelateGoods" stripe style="width: 100%">
                                <el-table-column prop="id" label="id" width="100"></el-table-column>
                                <el-table-column prop="list_pic_url" label="商品图片" width="120">
                                    <template scope="scope">
                                        <img :src="scope.row.list_pic_url" alt="" style="width: 40px;height: 40px">
                                    </template>
                                </el-table-column>
                                <el-table-column prop="name" label="商品名称" width="300px"></el-table-column>
                                <el-table-column label="操作">
                                    <template scope="scope">
                                        <el-button
                                                size="small"
                                                type="danger"
                                                @click="relateSelect(scope.row.id)">选择
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <el-button slot="reference" type="primary" @click="relateGoodsClick">添加关联商品</el-button>
                        </el-popover>
                    </el-form-item>
                    <el-form-item label="开始时间" prop="start_time">
                        <el-date-picker
                                v-model="infoForm.start_time"
                                format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
                                type="datetime"
                                placeholder="选择日期"
                                default-time="23:59:59">
                            >
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="结束时间" prop="end_time">
                        <el-date-picker
                                v-model="infoForm.end_time"
                                format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
                                type="datetime"
                                placeholder="选择日期"
                                default-time="23:59:59">
                            >
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="排序" prop="sort_order">
                        <el-input-number v-model="infoForm.sort_order" :min="1" :max="999"></el-input-number>
                    </el-form-item>
                    <el-form-item label="广告启用">
                        <el-switch active-value="1" inactive-value="0" v-model="infoForm.enabled"></el-switch>
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
	import qs from 'qs'

    export default {
        data() {
            return {
                qiniuZone:'',
                root: '',
                fileList: [],
                uploaderHeader: {
                    'X-Nideshop-Token': localStorage.getItem('token') || '',
                },
                infoForm: {
                    id: 0,
                    title:'',
                    info:'',
                    image_url: '',
                    link_type: 0,
                    enabled: 0,
                    start_time: '',
                    end_time: '',
                    goods_id:0,
                    link:''
                },
                infoRules: {
                    image_url: [
                        {required: true, message: '请输入广告图片', trigger: 'blur'},
                    ],
                    end_time: [
                        {required: true, message: '请选择结束时间', trigger: 'blur'},
                    ],
                },
                picData: {
                    token: '',
                    uc: 'ad'
                },
                url: '',
                chooseRelateGoods: [],
                related_pop: false
            }
        },
        methods: {
            relateSelect(id) {
                console.log(id);
                this.infoForm.goods_id = id;
                this.related_pop = false;
            },
            relateGoodsClick() {
                this.axios.post(this.root + 'ad/getallrelate', qs.stringify({id: this.infoForm.id})).then((response) => {
                    if (response.data.errno === 0) {
                        this.chooseRelateGoods = response.data.data
                    }
                });
            },
            test() {
                console.log(this.infoForm.end_time);
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
            beforeAdRemove() {
                return this.$confirm(`确定移除？`);
            },
            adRemove(file, fileList) {
                this.infoForm.image_url = '';
                let id = this.infoForm.id;
                this.axios.post(this.root + 'ad/deleteAdImage', qs.stringify({id: id})).then((response) => {
                    this.$message({
                        type: 'success',
                        message: '删除成功'
                    });
                });
            },
            getQiniuToken() {
                let that = this
                this.axios.post('index/getQiniuToken').then((response) => {
                    let resInfo = response.data.data;
                    console.log(resInfo);
                    that.picData.token = resInfo.token;
                    that.url = resInfo.url;
                })
            },
            goBackPage() {
                this.$router.go(-1);
            },
            onSubmitInfo() {
                console.log(this.infoForm);
                // return false;
                let time = this.infoForm.end_time
                if (time == 0) {
                    this.$message({
                        type: 'error',
                        message: '请选择时间'
                    });
                    return false;
                }
                if (this.infoForm.link_type == 0) {
                    if(this.infoForm.goods_id == 0 ){
                        this.$message({
                            type: 'error',
                            message: '请选择商品'
                        });
                        return false;
                    }
                }
                if (this.infoForm.link_type == 1) {
                    if(this.infoForm.link == '' ){
                        this.$message({
                            type: 'error',
                            message: '请输入链接'
                        });
                        return false;
                    }
                }
                this.$refs['infoForm'].validate((valid) => {
                    if (valid) {
                        this.axios.post(this.root + 'ad/store', qs.stringify(this.infoForm)).then((response) => {
                            if (response.data.errno === 0) {
                                this.$message({
                                    type: 'success',
                                    message: '保存成功'
                                });
                                this.$router.go(-1);
                            } else if (response.data.errno === 100) {
                                this.$message({
                                    type: 'error',
                                    message: '该商品已经有广告关联'
                                })
                            }
                            else {
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
            handleUploadImageSuccess(res, file) {
                let url = this.url;
                this.infoForm.image_url = res.data[0];
            },
            getInfo() {
            	/*
                if (this.infoForm.id <= 0) {
                    return false
                }
                */
                //加载广告详情
                let that = this
                this.axios.post(this.root + 'ad/info', qs.stringify({id: that.infoForm.id})
                ).then((response) => {
                    let resInfo = response.data.data;
                    let ad_info = resInfo.ad;
                    if(ad_info!=null) {
                    	ad_info.enabled = ad_info.enabled ? "1" : "0";
	                    that.infoForm = ad_info;
	                    //that.infoForm.end_time = resInfo.end_time * 1000;
	                    let info = {
	                        name: ad_info.name,
	                        url: ad_info.image_url
	                    };
	                    this.fileList.push(info);
	                    console.log(this.infoForm);
                    }
                    that.url = resInfo.prefix;
                    alert(that.url);
                })
            }
        },
        components: {},
        mounted() {
        	this.root = api.rootUrl;
            this.infoForm.id = this.$route.query.id || 0;
            this.getInfo();
            
            this.uploadAction = api.rootUrl + 'common/upload';
            //this.url = 'http://e.local:8080/img_server/';
            
            /*
            this.getQiniuToken();
            this.qiniuZone = api.qiniu;
            */
        }
    }

</script>

<style scoped>
    .image-show {
        background-color: #f8f8f8;
        min-width: 200px;
        height: 200px;
        display: block;
    }

    .id-input {
        margin-bottom: 20px;
    }

    .link-input .el-input__inner {
        width: 400px !important;
    }
</style>