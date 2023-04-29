#!/usr/bin/env bash
# SPDX-License-Identifier: Apache-2.0
#
# Copyright 2023 The Enola <https://enola.dev> Authors
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

set -euo pipefail

SCRIPT_DIR=$1
SCRIPT=$(realpath "$SCRIPT_DIR/script")
TOOLS_DIR=$(realpath "$(dirname "$0")")
CWD=$(pwd)
cd "$SCRIPT_DIR"

alias enola='$TOOLS_DIR/../../bazel-bin/cli/enola'
shopt -s expand_aliases
set -x
# shellcheck disable=SC1090
SLEEP=0 source "$SCRIPT"
set +x

cd "$CWD"