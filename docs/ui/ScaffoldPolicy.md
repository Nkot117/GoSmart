# Scaffold 設計ポリシー

本アプリにおけるScaffold / TopBar / FABの扱い方針をまとめ、画面追加時の迷いやコードの肥大化を防ぐことを目的とする。

- Scaffoldをアプリ全体で1箇所に統一しTopBarとFABのレイアウトの位置ズレを防ぐ。
- TopBarとFABの内容については、現在の画面（NavKey）から決定する。

## 全体構成

| レイヤ                     | 役割                                                                        |
| -------------------------- | --------------------------------------------------------------------------- |
| MainActivity / AppScaffold | `Scaffold`の定義。TopBar / FAB の適用。BackStack を保持し NavDisplay に渡す |
| Route（XxxScreenRoute）    | ViewModel / DI / 画面単位の初期設定                                         |
| Screen（XxxScreen）        | 純粋なUI描画（State/ViewModelに定義している関数を引数で受け取る）           |

## 実装方針

### 1. Scaffoldはアプリで1つだけ持つ

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

### 2. ScaffoldSpecでTopBar / FABを宣言する

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

#### 各画面のScreenコンポーザブルで画面のコンテンツを設定する

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
  2. NavDisplayのentryProviderにentryを追加
  3. scaffoldSpecForNavKey()にTopBar/FABの定義を追加
- Scaffoldの要素（TopBar/FAB など）は各Screenでは触れない。Screenは画面コンテンツのみを担当する。
- FABが不要な画面は ScaffoldSpec(fab = {})（デフォルト）で非表示にする。
- Scaffold によって確保されたTopBar/FABの占有領域をと各画面の表示要素の重なりを防止するため、Screen コンポーネントは contentPadding を受け取り、それをレイアウトに適用する。
