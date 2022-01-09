import MediumWidget from '../containers/MediumWidget';
import ShortageTableCell from '../containers/ShortageTableCell';
import Chart from 'chart.js/auto';
import React, {useEffect, useRef} from 'react';
import colorLib from '@kurkle/color';


import styles from '../styles/Home.module.scss';

function Home() {

  const chart1 = useRef<HTMLCanvasElement | null>(null);
  const chart2 = useRef<HTMLCanvasElement | null>(null);
  let labels:any =[ ];
  let data:any = [];

  for (let i:number = 1; i <= 30; i++) {
     labels.push(i);
     data.push(Math.floor(Math.random() * (200 - 5)) + 5);
  }

  function transparentize(value:any, opacity:any) {
  var alpha = opacity === undefined ? 0.5 : 1 - opacity;
  return colorLib(value).alpha(alpha).rgbString();
  }


  function colorize(opaque:any) {
  return (ctx:any) => {
    var v = ctx.parsed.y;
    var c = v < 50 ? '#D60000'
      : v < 100 ? '#F46300'
      : v < 200 ? '#0358B6'
      : '#44DE28';

    return opaque ? c : transparentize(c, 1 - Math.abs(v / 150));
  };
}

  let delayed:boolean;
  const config= {
  type: "bar",
  data: {
    labels:labels,
    datasets: [{
      data:data
    }],
  },
  options: {
    responsive: true,
    maintainAspectRatio:false,
    plugins: {
      legend: false
    },
    elements: {
      bar: {
        backgroundColor: colorize(false),
        borderColor: colorize(true),
        borderWidth: 2
      },
    },
    animation: {
      onComplete: () => {
        delayed = true;
    },
    delay: (context:any) => {
    let delay = 0;
    if (context.type === 'data' && context.mode === 'default' && !delayed) {
      delay = context.dataIndex * 150 + context.datasetIndex * 40;
    }
    return delay;
    },
  },


  },
  scales: {
  x: {
    stacked: true,
  },
  y: {
    stacked: true
  },
}
};

useEffect(() => {
  if(chart1
    && chart1.current){
      const myChart1 = new Chart(chart1.current, config as any);
    }
    if(chart2
      &&chart2.current){
      const myChart2 = new Chart(chart2.current, config as any);
    }


},[]);




  return (
      <div className={styles.main}>
        <div className={styles.widgets}>
          <MediumWidget number={254}>Wypożyczenia dziś</MediumWidget>
          <MediumWidget number={1024}>Pobrania dziś</MediumWidget>
          <MediumWidget number={5555}>Zwroty dziś</MediumWidget>
          <MediumWidget number={5555}>Nowych przedmiotów dziś</MediumWidget>
        </div>
        <div className={styles.dashboard}>
          <div className={styles.chart}>
          <h3>Wypożyczenia w ciągu<br/> ostatnich 30 dni</h3><div>
            <canvas className={styles.chartCanvas} ref={chart1}></canvas>
          </div>
          </div>
          <div className={styles.chart}>
            <h3>Pobrania w ciągu<br/> ostatnich 30 dni</h3><div>
            <canvas className={styles.chartCanvas} ref={chart2}></canvas>
            </div>
          </div>
          <div className={styles.list}>
          <h2>Deficyt</h2>

          <table>
            <thead>
              <tr>
                <th>PRZEDMIOT</th>
                <th>ILOŚĆ/POZIOM MINIMALNY</th>
                <th>ŚREDNIA TYGODNIOWA WYPOŻYCZEŃ</th>
                <th>POKRYCIE ILOŚCI MINIMALNEJ</th>
              </tr>
            </thead>
            <tbody>
              <ShortageTableCell>
                test
              </ShortageTableCell>
            </tbody>
          </table>
          </div>
        </div>

        <div>

        </div>
      </div>

  );
}

export default Home;
