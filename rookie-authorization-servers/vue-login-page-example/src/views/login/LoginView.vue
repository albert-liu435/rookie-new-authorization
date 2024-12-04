<script setup lang="ts">
import { ref } from 'vue'
import router from '../../router'
import { getQueryString } from '@/util/GlobalUtils'
import { type CountdownProps, createDiscreteApi } from 'naive-ui'
import { getImageCaptcha, getSmsCaptchaByPhone, loginSubmit } from '@/api/Login'

const { message } = createDiscreteApi(['message'])

// 登录按钮加载状态
const loading = ref(false)

// 定义登录提交的对象
const loginModel = ref({
  code: '',
  username: 'admin',
  password: '123456',
  loginType: '',
  captchaId: '',
})

// 图形验证码的base64数据
let captchaImage = ref('')
// 图形验证码的值
let captchaCode = ''
// 是否开始倒计时
const counterActive = ref(false)
// 是否显示三方登录
const showThirdLogin = ref(true)

// 生成二维码响应数据
const getQrCodeInfo = ref({
  qrCodeId: '',
  imageData: '',
})

// 是否自动提交授权确认(二维码登录自动提交)
const autoConsentKey: string = 'autoConsent'

/**
 * 获取图形验证码
 */
const getCaptcha = () => {
  getImageCaptcha()
    .then((result: any) => {
      if (result.success) {
        captchaCode = result.data.code
        captchaImage.value = result.data.imageData
        loginModel.value.captchaId = result.data.captchaId
      } else {
        message.warning(result.message)
      }
    })
    .catch((e: any) => {
      message.warning(`获取图形验证码失败：${e.message}`)
    })
}

/**
 * 提交登录表单
 * @param type 登录类型，passwordLogin是密码模式，smsCaptcha短信登录
 */
const submitLogin = (type: string) => {
  loading.value = true
  loginModel.value.loginType = type
  loginSubmit(loginModel.value)
    .then((result: any) => {
      if (result.success) {
        // 移除自动提交缓存
        localStorage.removeItem(autoConsentKey)
        // message.info(`登录成功`)
        let target = getQueryString('target')
        if (target) {
          window.location.href = target
        } else {
          // 跳转到首页
          router.push({ path: '/' })
        }
      } else {
        message.warning(result.message)
      }
    })
    .catch((e: any) => {
      message.warning(`登录失败：${e.message}`)
    })
    .finally(() => {
      loading.value = false
    })
}

/**
 * 获取短信验证码
 */
const getSmsCaptcha = () => {
  if (!loginModel.value.username) {
    message.warning('请先输入手机号.')
    return
  }
  if (!loginModel.value.code) {
    message.warning('请先输入验证码.')
    return
  }
  if (loginModel.value.code !== captchaCode) {
    message.warning('验证码错误.')
    return
  }
  getSmsCaptchaByPhone({ phone: loginModel.value.username })
    .then((result: any) => {
      if (result.success) {
        message.info(`获取短信验证码成功，固定为：${result.data}`)
        counterActive.value = true
      } else {
        message.warning(result.message)
      }
    })
    .catch((e: any) => {
      message.warning(`获取短信验证码失败：${e.message}`)
    })
}

/**
 * 切换时更新验证码
 * @param name tab的名字
 */
const handleUpdateValue = (name: string) => {
  // 二维码登录时隐藏三方登录
  showThirdLogin.value = name !== 'qrcode'
  if (!showThirdLogin.value) {
  } else {
    getCaptcha()
    // 切换账号登录或短信认证登录时填充默认的手机号/账号
    if (name === 'signup') {
      // 短信认证登录时
      loginModel.value.username = '17683906001'
      loginModel.value.password = ''
    } else {
      loginModel.value.username = 'admin'
      loginModel.value.password = '123456'
    }
  }
}

/**
 * 倒计时结束
 */
const onFinish = () => {
  counterActive.value = false
}

/**
 * 倒计时显示内容
 */
const renderCountdown: CountdownProps['render'] = ({
  hours,
  minutes,
  seconds,
}) => {
  return `${seconds}`
}

/**
 * 根据类型发起OAuth2授权申请
 * @param type 三方OAuth2登录提供商类型
 */
const thirdLogin = (type: string) => {
  window.location.href = `${import.meta.env.VITE_OAUTH_ISSUER}/oauth2/authorization/${type}`
}

getCaptcha()
</script>

<template>
  <header>
    <img
      alt="Vue logo"
      class="logo"
      src="../../assets/logo.svg"
      width="125"
      height="125"
    />

    <div class="wrapper">
      <HelloWorld msg="统一认证平台" />
    </div>
  </header>

  <main>
    <n-card title="">
      <n-tabs
        default-value="signin"
        size="large"
        justify-content="space-evenly"
        @update:value="handleUpdateValue"
      >
        <n-tab-pane name="signin" tab="账号登录">
          <n-form>
            <n-form-item-row label="用户名">
              <n-input
                v-model:value="loginModel.username"
                placeholder="手机号 / 邮箱"
              />
            </n-form-item-row>
            <n-form-item-row label="密码">
              <n-input
                v-model:value="loginModel.password"
                type="password"
                show-password-on="mousedown"
                placeholder="密码"
              />
            </n-form-item-row>
            <n-form-item-row label="验证码">
              <n-input-group>
                <n-input
                  v-model:value="loginModel.code"
                  placeholder="请输入验证码"
                />
                <n-image
                  @click="getCaptcha"
                  width="130"
                  height="34"
                  :src="captchaImage"
                  preview-disabled
                />
              </n-input-group>
            </n-form-item-row>
          </n-form>
          <n-button
            type="info"
            :loading="loading"
            @click="submitLogin('passwordLogin')"
            block
            strong
          >
            登录
          </n-button>
        </n-tab-pane>
        <n-tab-pane name="signup" tab="短信登录">
          <n-form>
            <n-form-item-row label="手机号">
              <n-input
                v-model:value="loginModel.username"
                placeholder="手机号 / 邮箱"
              />
            </n-form-item-row>
            <n-form-item-row label="验证码">
              <n-input-group>
                <n-input
                  v-model:value="loginModel.code"
                  placeholder="请输入验证码"
                />
                <n-image
                  @click="getCaptcha"
                  width="130"
                  height="34"
                  :src="captchaImage"
                  preview-disabled
                />
              </n-input-group>
            </n-form-item-row>
            <n-form-item-row label="验证码">
              <n-input-group>
                <n-input
                  v-model:value="loginModel.password"
                  placeholder="请输入验证码"
                />
                <n-button
                  type="info"
                  @click="getSmsCaptcha"
                  style="width: 130px"
                  :disabled="counterActive"
                >
                  获取验证码
                  <span v-if="counterActive">
                    (
                    <n-countdown
                      :render="renderCountdown"
                      :on-finish="onFinish"
                      :duration="59 * 1000"
                      :active="counterActive"
                    />
                    )</span
                  >
                </n-button>
              </n-input-group>
            </n-form-item-row>
          </n-form>
          <n-button
            type="info"
            :loading="loading"
            @click="submitLogin('smsCaptcha')"
            block
            strong
          >
            登录
          </n-button>
        </n-tab-pane>
        <!--        <n-tab-pane name="qrcode" tab="扫码登录" style="text-align: center">-->
        <!--          <div style="margin: 5.305px">-->
        <!--            <n-image width="300" :src="getQrCodeInfo.imageData" preview-disabled />-->
        <!--          </div>-->
        <!--        </n-tab-pane>-->
      </n-tabs>
      <n-divider style="font-size: 80%; color: #909399">
        {{ showThirdLogin ? '其它登录方式' : '使用app扫描二维码登录' }}
      </n-divider>
      <div class="other_login_icon" v-if="showThirdLogin">
        <IconGitee :size="32" @click="thirdLogin('gitee')" class="icon_item" />
        <img
          width="36"
          height="36"
          @click="thirdLogin('github')"
          src="../../assets/GitHub-Mark.png"
          class="icon_item"
        />
        <img
          width="28"
          height="28"
          @click="thirdLogin('wechat')"
          src="../../assets/wechat_login.png"
          class="icon_item"
        />
      </div>
    </n-card>
  </main>
</template>

<style scoped>
.other_login_icon {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0 10px;
  position: relative;
  margin-top: -5px;
}

.icon_item {
  cursor: pointer;
}

header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
