# Useful ParticleEffect / ParticleEmmitter code

## Get the first emitter (I'm generally only using one)

```{java}
ParticleEmitter emitter = explosion.getEmitters().first();
```

## Change duration of emitter

```{java}
ParticleEmitter.RangedNumericValue duration = emitter.getDuration();
duration.setLow(2000, 2000);
```

## Change tint of emitter 

```{java}
ParticleEmitter.GradientColorValue tint = emitter.getTint();
float[] colors = new float[3];
colors[0] = 51f / 255;
colors[1] = 51f / 255;
colors[2] = 1;
tint.setColors(colors);
```