# Scaffold 設計ポリシー

本アプリにおける Scaffold / TopBar / FAB の扱い方針をまとめ、画面追加時の迷いやコードの肥大化を防ぐことを目的とする。

- Scaffold をアプリ全体で 1 箇所に統一し、TopBar と FAB のレイアウトの位置ズレを防ぐ。
- TopBar と FAB の内容については、各画面で設定する。

### 全体構成

| レイヤ                         | 役割                                            | 例                                        |
| ------------------------------ | ----------------------------------------------- | ----------------------------------------- |
| MainActivity（または AppRoot） | `Scaffold` の定義。TopBar / FAB のスロット管理  | `Scaffold(topBar = { … }, fab = { … })`   |
| \*Route（XxxScreenRoute）      | ViewModel / DI / 画面単位の設定                 | `LaunchedEffect` で TopBar / FAB 差し替え |
| Screen（XxxScreen）            | 純粋な UI 描画（状態/イベントを引数で受け取る） | `Column` / `LazyColumn` などの構成のみ    |

### 実装方針

#### MainActivity に 1 つだけ Scaffod を置く

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartGoTheme {
                var topBar by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
                var fab by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

                Scaffold(
                    topBar = { topBar?.invoke() },
                    floatingActionButton = { fab?.invoke() }
                ) { inner ->
                    AppNavHost(
                        contentPadding = inner,
                        setTopBar = { topBar = it },
                        setFab = { fab = it }
                    )
                }
            }
        }
    }
}
```

#### 各画面の Route コンポーザブルで TopBar と FAB の設定を行う

```kotlin
@Composable
fun HomeScreenRoute(
contentPadding: PaddingValues,
setTopBar: (@Composable () -> Unit) -> Unit,
setFab: (@Composable () -> Unit) -> Unit,
viewModel: HomeViewModel = hiltViewModel(),
) {
val state by viewModel.uiState.collectAsStateWithLifecycle()

    // 初回描画時に画面ごとの TopBar / FAB を差し替える
    LaunchedEffect(Unit) {
        setTopBar { AppTopBar(title = "ホーム") }
        setFab {
            FloatingActionButton(onClick = { /* navigate */ }) {
                Icon(Icons.Default.Add, contentDescription = "追加")
            }
        }
    }

    // 画面のメインコンテンツを定義するScreenコンポーザブルを呼び出す
    HomeScreen(
        state = state,
        onClickItem = viewModel::onItemClick,
        contentPadding = contentPadding
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
