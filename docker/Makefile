define compose
	@docker-compose -p pet-photoapp $(1)
endef

up:
	$(call compose, up -d)

ps:
	$(call compose, ps)

stop:
	$(call compose, stop)

down:
	$(call compose, down)

restart:
	$(call compose, restart)
