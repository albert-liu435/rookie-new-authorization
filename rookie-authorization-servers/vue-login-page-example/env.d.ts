/// <reference types="vite/client" />
interface ImportMetaEnv {
  readonly VITE_OAUTH_ISSUER: string
  readonly VITE_PKCE_CLIENT_ID: string
  readonly VITE_OAUTH_CLIENT_ID: string
  readonly VITE_PKCE_REDIRECT_URI: string
  readonly VITE_OAUTH_REDIRECT_URI: string
  readonly VITE_OAUTH_CLIENT_SECRET: string
}
