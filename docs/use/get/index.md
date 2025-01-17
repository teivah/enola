<!--
    SPDX-License-Identifier: Apache-2.0

    Copyright 2023 The Enola <https://enola.dev> Authors

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
-->

# Get Entity

`enola get` will retrieve an _entity_ from its [connectors](../connector/index.md).

## Screencast (Asciinema)

![Demo](script.svg)

## Enola Get

Get a `book` - note how the _related_ `kind` and `library` ID are set, based on the template
[from the model](../library/index.md):

```bash cd .././.././..
$ ./enola get --model file:docs/use/library/model.yaml demo.book/ABC/0-13-140731-7/1
...
```

Get a `book_kind` - note how the `google` _link_ was set, based on the template
[from the model](../library/index.md):

```bash cd .././.././..
$ ./enola get --model file:docs/use/library/model.yaml demo.book_kind/0-13-140731-7
...
```
