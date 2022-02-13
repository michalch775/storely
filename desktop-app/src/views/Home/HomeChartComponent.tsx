import Chart from 'chart.js/auto';
import React, {useEffect, useRef} from 'react';
import colorLib from '@kurkle/color';
import {inspect} from "util";
import styles from "../../styles/HomeChartComponent.module.scss";

function HomeChartComponent() {

    const chart = useRef<HTMLCanvasElement | null>(null);
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
        if(chart
            && chart.current){
            const myChart1 = new Chart(chart.current, config as any);
        }
    },[]);




    return (
        <canvas className = {styles.main} ref={chart}>Unknown error</canvas>
    );
}

export default HomeChartComponent;
