module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  daisyui: {
    themes: [
      {
        pastel: {
          ...require("daisyui/src/colors/themes")["[data-theme=pastel]"],
          'base-100':'#D1C1D7'
        },
      },
    ],
  },
  theme: 
  { colors: {
    transparent: 'transparent',
    current: 'currentColor',
    'white': '#ffffff',
    'purple': '#3f3cbb',
    'midnight': '#121063',
    'metal': '#565584',
    'tahiti': '#3ab7bf',
    'silver': '#ecebff',
    'bubble-gum': '#ff77e9',
    'bermuda': '#78dcca',
  },
    extend: {
      keyframes: {
        'fade-in-down': {
          '0%': {
            opacity: '0'
          },
          '100%': {
            opacity: '1'
          }
        }
      },
      animation: {
        pulse: 'pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite',
        'ping-slow': 'ping 2s cubic-bezier(0, 0, 0.2, 1) infinite',
        'ping-moderato': 'ping 1.5s cubic-bezier(0, 0, 0.2, 1) infinite',
        'ping-fast': 'ping 1s cubic-bezier(0, 0, 0.2, 1) infinite',
        'fade-in-down': 'fade-in-down 0.5s ease-in'
      },
    }
  },

  plugins: [require("daisyui")],

}