## Overview

本アプリでは UI Layer / Domain Layer / Data Layer の 3 層アーキテクチャを採用しています。
アプリの中核となるビジネスロジックは Domain Layer に定義し、UI とデータ取得処理は Domain に依存する形で構成しています。
UI Layer や Data Layer は変更されやすい領域であるため、それらに依存しない不変なビジネスロジック（Domain 層）を中心におくことで、変更に強い構造となっています。

## Module Structure

本アプリでは、3 層アーキテクチャを明確に保ち、機能ごとに責務を分離するため、マルチモジュール構成を採用しています。
依存方向はビジネスロジックを定義する:core:domain を中心とした一方向となっています。
います。
モジュール間の依存関係の全体像は、以下のドキュメントにまとめています。

```
docs/airchitecture/ModuleStructure.drawio
```
