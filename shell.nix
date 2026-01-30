{ pkgs ? import <nixpkgs> {} }:
# Creating an FHS environemnt is useful for NixOS because io.mvnpm.esbuild.deno
# uses Deno from node_modules which uses a binary intended for generic
# environments. This is used for the Web Bundler extension.
(pkgs.buildFHSEnv {
  name = "hofkiinsewundun-env";
}).env
