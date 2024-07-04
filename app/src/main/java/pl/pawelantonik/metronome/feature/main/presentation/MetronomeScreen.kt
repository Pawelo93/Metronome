package pl.pawelantonik.metronome.feature.main.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.pawelantonik.metronome.R
import pl.pawelantonik.metronome.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetronomeScreen() {
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
          containerColor = AppTheme.colors.background,
          navigationIconContentColor = Color.White
        ),
        title = {
          Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(id = R.drawable.music_logo),
            colorFilter = ColorFilter.tint(AppTheme.colors.primary),
            contentDescription = null,
          )
        },
      )
    },
    content = { paddingValues ->
      Column(
        modifier = Modifier
          .padding(paddingValues)
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Spacer(modifier = Modifier.height(32.dp))

        Counter(
          selectedNumber = 2,
        )

        MainRoundButton(
          isEnabled = false,
          durationMillis = 900,
        )

        Spacer(modifier = Modifier.height(32.dp))

        BottomOptions()
      }
    },
  )
}

@Composable
private fun Counter(
  selectedNumber: Int,
) {

  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
    val textStyle = AppTheme.typography.headingH1.copy(color = AppTheme.colors.onPrimary, fontSize = 52.sp)
    Column {
      Text(
        style = textStyle,
        text = "-",
      )
    }

    Column {
      var selectedValue by remember {
        mutableIntStateOf(100)
      }

      SwipeValueSelector(
          initialValue = selectedValue,
          onValueChange = { newValue ->
            selectedValue = newValue
            // Do something with the new value if needed
          }
        )
//      Text(
//        style = AppTheme.typography.headingH1.copy(
//          fontSize = TextUnit(
//            80f,
//            TextUnitType.Sp
//          ), fontWeight = FontWeight.Bold,
//          color = AppTheme.colors.onPrimary,
//        ),
//        text = "100",
//      )
    }

    Column {
      Text(
        style = textStyle,
        text = "+",
      )
    }
  }

  Spacer(modifier = Modifier.height(16.dp))

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceAround,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row {
      for (i in listOf(1, 2, 5, 10)) {
        Text(
          modifier = Modifier
            .padding(start = AppTheme.spacings.small, end = AppTheme.spacings.small)
            .then(
              if (i == selectedNumber) {
                Modifier.background(
                  color = AppTheme.colors.primary,
                  shape = CircleShape,
                )
              } else {
                Modifier
              }
            )
            .size(30.dp)
            .wrapContentSize(),
          text = i.toString(),
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MetronomeScreenPreview() {
  AppTheme {
    MetronomeScreen()
  }
}