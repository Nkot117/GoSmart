# Scaffold 設計ポリシー

本アプリにおける Scaffold / TopBar / FAB の扱い方針をまとめ、画面追加時の迷いやコードの肥大化を防ぐことを目的とする。

- Scaffold をアプリ全体で 1 箇所に統一し、TopBar と FAB のレイアウトの位置ズレを防ぐ。
- TopBar と FAB の内容については、現在の画面（NavKey）から決定する。

## 全体構成

| レイヤ                     | 役割                                                                         |
| -------------------------- | ---------------------------------------------------------------------------- |
| MainActivity / AppScaffold | `Scaffold` の定義。TopBar / FAB の適用。BackStack を保持し NavDisplay に渡す |
| Route（XxxScreenRoute）    | ViewModel / DI / 画面単位の初期設定                                          |
| Screen（XxxScreen）        | 純粋な UI 描画（State/ViewModel に定義している関数を引数で受け取る）         |

## 実装方針

### 1. Scaffold はアプリで 1 つだけ持つ

```kotlin
@Composable
fun AppScaffold() {
    val navigator = remember { Navigator() }
    val scaffoldSpec = scaffoldSpecForNavKey(
        appNavKey = navigator.current,
        onBack = { navigator.pop() }
    )

    Scaffold(
        topBar = scaffoldSpec.topBar,
        floatingActionButton = scaffoldSpec.fab
    ) { innerPadding ->
        NavDisplay(
            backStack = navigator.backStack,
            onBack = { navigator.pop() },
            entryProvider = entryProvider {
                entry(AppNavKey.Home) {
                    HomeScreenRoute(
                        contentPadding = innerPadding,
                        onTapCheckList = { navigator.push(AppNavKey.Checklist) }
                    )
                }
                entry<AppNavKey.Checklist> {
                    ChecklistScreenRoute(contentPadding = innerPadding)
                }
            }
        )
    }
}
```

### 2. ScaffoldSpec で TopBar / FAB を宣言する

```kotlin
data class ScaffoldSpec(
    val topBar: @Composable () -> Unit = {},
    val fab: @Composable () -> Unit = {},
)

private fun scaffoldSpecForNavKey(
    appNavKey: AppNavKey,
    onBack: () -> Unit,
): ScaffoldSpec = when (appNavKey) {
    AppNavKey.Home -> ScaffoldSpec(
        topBar = { AppTopBar(title = "ホーム") },
        fab = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    )

    AppNavKey.Checklist -> ScaffoldSpec(
        topBar = { AppTopBar(title = "チェックリスト", onBack = onBack) }
    )
}
```

#### 各画面の Screen コンポーザブルで 画面のコンテンツを設定する

```kotlin
@Composable
fun HomeScreen(
    state: HomeUiState,
    onClickItem: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        // ... UIのみ記述
    }
}
```

#### 運用ルール

- 画面追加時は以下を実施する。
  1. AppNavKey を追加
  2. NavDisplay の entryProvider に entry を追加
  3. scaffoldSpecForNavKey() に TopBar / FAB の定義を追加
- Scaffold の要素（TopBar / FAB など）は各 Screen では触れない。Screen は画面コンテンツのみを担当する。
- FAB が不要な画面は ScaffoldSpec(fab = {})（デフォルト）で非表示にする。
- Scaffold によって確保された TopBar / FAB の占有領域をと各画面の表示要素の重なりを防止するため、Screen コンポーネントは contentPadding を受け取り、それをレイアウトに適用する。
